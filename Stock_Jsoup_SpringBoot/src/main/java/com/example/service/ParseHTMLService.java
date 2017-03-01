package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.example.models.MonthCompositeKey;
import com.example.models.QuartCompositeKey;
import com.example.models.StockInfoBean;
import com.example.models.StockMonthlyBean;
import com.example.models.StockQuarterlyBean;

@Component
public class ParseHTMLService {
	@Value("${yahooURL}")
	private String yahooURL;
	@Value("${stockInfoURL}")
	private String stockInfoURL;
	@Value("${stockEarningURL}")
	private String stockEarningURL;
	@Value("${userAgent}")
	private String userAgent;
	@Value("${acceptType}")
	private String acceptType;
	@Value("${acceptEncoding}")
	private String acceptEncoding;
	@Value("${acceptLanguage}")
	private String acceptLanguage;
	@Value("${cookieFail}")
	private String cookieFail;
	@Value("${noImgURL}")
	private String noImgURL;
	@Autowired
	private ApplicationContext context;
	private Map<String, String> cookies = null;

	public ParseHTMLService() {

	}

	// 模擬使用者使用網頁進入yahoo股票 防止被當成機器人阻擋
	private Document getDocFromURL(String url, String referer) throws IOException {
		Document doc = null;
		doc = Jsoup.connect(url).header("Accept", acceptType).header("Accept-Encoding", acceptEncoding)
				.header("Accept-Language", acceptLanguage).header("Connection", "keep-alive").header("Host", yahooURL)
				.header("Cache-Control", "max-age=0").header("Upgrade-Insecure-Requests", "1")
				.header("Referer", referer).userAgent(userAgent).cookies(cookies).get();

		return doc;
	}

	// 外部透過這個方法獲得完整的股票資訊
	public StockInfoBean getStockTotalInfo(int stockNum) {

		String infoWebURL = stockInfoURL + stockNum + ".html";
		String earnWebURL = stockEarningURL + stockNum + ".html";

		// 使用cookies模仿使用者行為 防止被偵測是使用程式抓取資料
		try {
			Response res = Jsoup.connect(infoWebURL).userAgent(userAgent).execute();
			cookies = res.cookies();
		} catch (IOException e) {
			// 無此股票 將會獲取cookies失敗
			// e.printStackTrace();
			System.out.println(cookieFail);
			return null;
		}

		Element body;
		StockInfoBean stockInfoBean;
		try {
			// 模仿使用者透過瀏覽器觀看股票資訊 使用query字串提供推薦頁面(referer)
			Document doc = getDocFromURL(infoWebURL, yahooURL + "q?s=" + stockNum);
			body = doc.body(); // 取出Body標籤(元素)
			// 先取得股票的基本資料
			stockInfoBean = getStockInfo(body, stockNum);
			// 股票資訊 與 股票營收盈餘資料(包括股票走勢圖) 在不同頁面 所以必須重新獲取html
			doc = getDocFromURL(earnWebURL, infoWebURL);
			body = doc.body();
			// 取得股票走勢圖
			setStockChart(doc, stockInfoBean);
			// 取得股票每月營收變化
			stockInfoBean.setStockMonthlyBeans(getStock_Monthly_Info(body, stockInfoBean));
			// 取得股票每季稅前/稅後盈餘變化
			stockInfoBean.setStockQuarterlyBean(getStock_Quart_Info(body, stockInfoBean));
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return stockInfoBean;
	}

	// 股票的基本資料
	private StockInfoBean getStockInfo(Element body, int stockNum) throws IOException {
		StockInfoBean stockInfoBean = context.getBean(StockInfoBean.class);
		stockInfoBean.setStockID(stockNum);
		// 股聘代號名稱
		Elements stockNameElement = body.select("[color=#F70000]");
		for (Element e : stockNameElement) {
			stockInfoBean.setStockName(e.text().substring(4, e.text().length()).trim());
			break;
		}

		// 公司type 董事長
		Elements businessInfoElement = body.select("tr[bgcolor*=#FFFFFF]");
		int count = 0;
		for (Element e : businessInfoElement) {
			count += 1;
			// 只有1與4是所需資料
			if (count == 1) {
				Elements etd = e.select("td[align=left]");
				// System.out.println(etd.text());
				stockInfoBean.setBusinessType(etd.text().trim());
				continue;
			}
			if (count == 4) {
				Elements etd = e.select("td[align=left]");
				stockInfoBean.setPresident((etd.text().trim()));
				break;
			}
		}
		// 股本
		count = 0;
		Elements capitalElement = body.getElementsByClass("yui-td-left");
		for (Element e : capitalElement) {
			count += 1;
			if (count == 3) {
				// System.out.println(e.ownText());
				stockInfoBean.setCapital(e.ownText().trim());
				break;
			}
		}

		return stockInfoBean;

	}

	// 股票走勢圖
	private void setStockChart(Document doc, StockInfoBean info) {
		// 走勢圖
		Elements stockChartElement = doc.select("[width=560]");
		int count = 0;
		for (Element e : stockChartElement) {
			String[] strArray = e.toString().split("\"");
			// System.out.println(Arrays.toString(strArray));
			if (count == 0) {
				info.setMonthlyRevenueChart(getByteArrayFromChart(strArray[1]));
				count++;
				continue;
			} else {
				info.setQuarterlyRevenueChart(getByteArrayFromChart(strArray[1]));
				break;
			}
		}
		info.setLastUpdate(new Date(new java.util.Date().getTime()));

	}

	// 股票每季稅前/稅後盈餘變化
	private LinkedHashSet<StockQuarterlyBean> getStock_Quart_Info(Element body, StockInfoBean info) {
		List<StockQuarterlyBean> list = new ArrayList<>();
		Elements trElement = body.select("tr[bgcolor*=#FFFFFF]");
		int count = 0;
		for (Element e : trElement) {
			Elements tdElement = e.getElementsByTag("td");
			// 當tdElement中存放的資料size達到7 才是我們可用資料
			// 不到7 代表資料不符合 可以直接繼續迴圈
			if (tdElement.size() != 7) {
				continue;
			}
			// 由於yahoo中股票每季的資料被分割成2部分 而設計的bean並沒有分割 所以使用count進行統計 4以前與4以後
			// 要存取的資料不同
			// 也因為如此 必須使用list來存放資料 可以使用get(int index)方法將bean取出 獲得bean之後再繼續放入資料
			if (count < 4) {
				StockQuarterlyBean quartInfo = context.getBean(StockQuarterlyBean.class);
				// Stock_quartBean quartInfo = new Stock_quartBean();
				QuartCompositeKey quartCompKey = context.getBean(QuartCompositeKey.class);
				// 股票代號
				quartCompKey.setStockID(info.getStockID());

				// Element中所需要的資訊需使用ownText()獲取並使用trim()去除空白
				// 獲取之後 進行型態的轉換存入資料庫 ex: String -> int, String -> long, String
				// ->BigDecimal

				// 季
				quartCompKey.setQuarterly(Integer.valueOf(tdElement.get(0).ownText().trim()));
				quartInfo.setQuartCompositeKey(quartCompKey);
				// 104年度稅後盈餘
				// note: AT = after tax; BT=before tax;
				quartInfo.setProfitAT104(convertStringToLong(tdElement.get(1).ownText().trim()) * 1000);
				// 104年度稅後年增率
				quartInfo.setProfitRateAT104(tdElement.get(2).ownText().trim());
				// 105年度稅後盈餘
				quartInfo.setProfitAT105(convertStringToLong(tdElement.get(4).ownText().trim()) * 1000);
				// 105年度稅後年增率
				quartInfo.setProfitRateAT105(tdElement.get(5).ownText().trim());
				// 105年度稅後達成率
				quartInfo.setAchieveRateAT105(tdElement.get(6).ownText().trim());
				quartInfo.setStockInfoBean(info);
				count++;
				list.add(quartInfo);
			} else if (count >= 4) {
				// 將先前已經放入的bean取出 再繼續放入相關資訊
				StockQuarterlyBean quartInfo = list.get(count - 4);
				// 104年度稅前盈餘
				quartInfo.setProfitBT104(convertStringToLong(tdElement.get(1).ownText().trim()) * 1000);
				// 104年度稅前年增率
				quartInfo.setProfitRateBT104(tdElement.get(2).ownText().trim());
				// 105年度稅前盈餘
				quartInfo.setProfitBT105(convertStringToLong(tdElement.get(4).ownText().trim()) * 1000);
				// 105年度稅前年增率
				quartInfo.setProfitRateBT105(tdElement.get(5).ownText().trim());
				// 105年度稅前達成率
				quartInfo.setAchieveRateBT105(tdElement.get(6).ownText().trim());
				count++;
			}
		}

		return new LinkedHashSet<>(list);

	}

	// 股票每月營收變化
	private LinkedHashSet<StockMonthlyBean> getStock_Monthly_Info(Element body, StockInfoBean info) {
		LinkedHashSet<StockMonthlyBean> set = new LinkedHashSet<>();

		Elements trElement = body.select("tr[bgcolor*=#FFFFFF]");

		for (Element e : trElement) {
			StockMonthlyBean month_Info = context.getBean(StockMonthlyBean.class);
			Elements tdElement = e.getElementsByTag("td");
			if (tdElement.size() == 9) {
				MonthCompositeKey monCompKey = context.getBean(MonthCompositeKey.class);
				// 股票代號
				monCompKey.setStockID(info.getStockID());
				// 月份
				if (tdElement.get(0).ownText().trim().length() == 1) {
					monCompKey.setMonth("0" + tdElement.get(0).ownText().trim());
				} else {
					monCompKey.setMonth(tdElement.get(0).ownText().trim());
				}
				month_Info.setMonthCompositeKey(monCompKey);
				// 105年度營收
				month_Info.setRevenueIn105(convertStringToLong(tdElement.get(1).ownText().trim()) * 1000);
				// 105年度年增率
				month_Info.setIncreaseRateIn105(convertStringToBigDecimal(tdElement.get(2).ownText().trim()));
				// 106年度營收
				month_Info.setRevenueIn106(convertStringToLong(tdElement.get(4).ownText().trim()) * 1000);
				// 106年度年增率
				month_Info.setIncreaseRateIn106(convertStringToBigDecimal(tdElement.get(5).ownText().trim()));
				// 106年度累計營收
				month_Info.setAccumulateRevenueIn106(convertStringToLong(tdElement.get(6).ownText().trim()) * 1000);
				// 106年度年增率
				month_Info.setAccumulateIncreaseRateIn106(convertStringToBigDecimal(tdElement.get(7).ownText().trim()));
				// 106年度達成率
				month_Info.setAchieveRateIn106(tdElement.get(8).ownText().trim());
				month_Info.setStockInfoBean(info);
				set.add(month_Info);
			}
		}

		return set;

	}

	// covert Graph to byte array
	private byte[] getByteArrayFromChart(String url) {

		// 如果有資料但是沒有走勢圖 放入一張"找不到圖"的圖片進入資料庫
		if (url == null || url.equals(" ") || url.equalsIgnoreCase("NULL")) {
			url = noImgURL;
		} else {
			url = yahooURL + url;
		}

		try {
			URLConnection con = new URL(url).openConnection();
			con.addRequestProperty("User-Agent", userAgent);
			try (InputStream is = con.getInputStream(); ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
				int count = 0;
				byte[] ba = new byte[8192];
				while ((count = is.read(ba)) != -1) {
					baos.write(ba, 0, count);
				}
				return baos.toByteArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	// convert String to long value
	private long convertStringToLong(String s) {
		long n = 0;
		s = s.replace(",", "");
		if (s.length() == 1) {
			s = s.replace("-", "0");
		}
		try {
			n = Long.parseLong(s.trim());
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
		return n;
	}

	// convert String to BigDecimal
	private BigDecimal convertStringToBigDecimal(String s) {
		s = s.replace(",", "");
		s = s.replace("%", "");
		if (s.length() == 1) {
			s = s.replace("-", "0");
		}
		BigDecimal d = new BigDecimal(s.trim());

		return d;
	}
}
