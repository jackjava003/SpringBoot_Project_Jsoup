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

import com.example.models.Month_CompositeKey;
import com.example.models.Quart_CompositeKey;
import com.example.models.Stock_infoBean;
import com.example.models.Stock_monthBean;
import com.example.models.Stock_quartBean;

@Component
public class Parse_HTML_Service {
	@Value("${buri}")
	private String buri;
	@Value("${stockinfo}")
	private String stock_info;
	@Value("${stockearning}")
	private String stock_earning;
	@Value("${userAgent}")
	private String userAgent;
	@Value("${Accept}")
	private String accept;
	@Value("${AcceptEncoding}")
	private String acceptEncoding;
	@Value("${AcceptLanguage}")
	private String acceptLanguage;
	@Value("${cookieFail}")
	private String cookieFail;
	@Value("${noImg}")
	private String noImg;
	@Autowired
	private ApplicationContext context;
	private int stockNum;
	Map<String, String> cookies = null;

	public Parse_HTML_Service() {

	}

	private Document getDoc(String url, String referer) throws IOException {
		Document doc = null;
		doc = Jsoup.connect(url).header("Accept", accept).header("Accept-Encoding", acceptEncoding)
				.header("Accept-Language", acceptLanguage).header("Connection", "keep-alive").header("Host", buri)
				.header("Cache-Control", "max-age=0").header("Upgrade-Insecure-Requests", "1")
				.header("Referer", referer).userAgent(userAgent).cookies(cookies).get();

		return doc;
	}

	public LinkedHashSet<Stock_quartBean> getStock_Quart_Info(Element body, Stock_infoBean info) {
		List<Stock_quartBean> list = new ArrayList<>();
		Elements els = body.select("tr[bgcolor*=#FFFFFF]");
		int n = 0;
		for (Element e : els) {
			Elements etd2 = e.getElementsByTag("td");
			if (etd2.size() == 7 && n < 4) {
				Stock_quartBean quartInfo = context.getBean(Stock_quartBean.class);
				// Stock_quartBean quartInfo = new Stock_quartBean();
				Quart_CompositeKey qComp = context.getBean(Quart_CompositeKey.class);
				//股票代號
				qComp.setStockID(stockNum);
				//季 
				qComp.setQuarterly(Integer.valueOf(etd2.get(0).ownText().trim()));
				quartInfo.setQuart_CompositeKey(qComp);
				//104年度稅後盈餘
				quartInfo.setProfit_AT_104(str2Long(etd2.get(1).ownText().trim()) * 1000);
				//104年度稅後年增率
				quartInfo.setProfit_rate_AT_104(etd2.get(2).ownText().trim());
				//105年度稅後盈餘
				quartInfo.setProfit_AT_105(str2Long(etd2.get(4).ownText().trim()) * 1000);
				//105年度稅後年增率
				quartInfo.setProfit_rate_AT_105(etd2.get(5).ownText().trim());
				//105年度稅後達成率
				quartInfo.setAchieve_rate_AT_105(etd2.get(6).ownText().trim());
				quartInfo.setStock_infoBean(info);
				n++;
				list.add(quartInfo);
			} else if (etd2.size() == 7 && n >= 4) {
				Stock_quartBean quartInfo = list.get(n - 4);
				//104年度稅前盈餘
				quartInfo.setProfit_BT_104(str2Long(etd2.get(1).ownText().trim()) * 1000);
				//104年度稅前年增率
				quartInfo.setProfit_rate_BT_104(etd2.get(2).ownText().trim());
				//105年度稅前盈餘
				quartInfo.setProfit_BT_105(str2Long(etd2.get(4).ownText().trim()) * 1000);
				//105年度稅前年增率
				quartInfo.setProfit_rate_BT_105(etd2.get(5).ownText().trim());
				//105年度稅前達成率
				quartInfo.setAchieve_rate_BT_105(etd2.get(6).ownText().trim());
				n++;
			}
		}

		return new LinkedHashSet<>(list);

	}

	//
	public LinkedHashSet<Stock_monthBean> getStock_Monthly_Info(Element body, Stock_infoBean info) {
		LinkedHashSet<Stock_monthBean> set = new LinkedHashSet<>();

		Elements els = body.select("tr[bgcolor*=#FFFFFF]");

		for (Element e : els) {
			Stock_monthBean month_Info = context.getBean(Stock_monthBean.class);
			Elements etd2 = e.getElementsByTag("td");
			if (etd2.size() == 9) {
				Month_CompositeKey mc = context.getBean(Month_CompositeKey.class);
				//股票代號
				mc.setStockID(stockNum);
				//月份
				if (etd2.get(0).ownText().trim().length() == 1) {
					mc.setMonth("0" + etd2.get(0).ownText().trim());
				} else {
					mc.setMonth(etd2.get(0).ownText().trim());
				}
				month_Info.setMonth_CompositeKey(mc);
				//105年度營收
				month_Info.setRev_105(str2Long(etd2.get(1).ownText().trim()) * 1000);
				//105年度年增率
				month_Info.setIncr_rate_105(str2BigDecimal(etd2.get(2).ownText().trim()));
				//106年度營收
				month_Info.setRev_106(str2Long(etd2.get(4).ownText().trim()) * 1000);
				//106年度年增率
				month_Info.setIncr_rate_106(str2BigDecimal(etd2.get(5).ownText().trim()));
				//106年度累計營收
				month_Info.setCumu_rev_106(str2Long(etd2.get(6).ownText().trim()) * 1000);
				//106年度年增率
				month_Info.setAnnu_rate_106(str2BigDecimal(etd2.get(7).ownText().trim()));
				//106年度達成率
				month_Info.setAchieve_rate_106(etd2.get(8).ownText().trim());
				month_Info.setStock_infoBean(info);
				set.add(month_Info);
			}
		}

		return set;

	}

	public Stock_infoBean getStockInfo(int stockNum) {

		String infoWeb = stock_info + stockNum + ".html";
		String earnWeb = stock_earning + stockNum + ".html";
		this.stockNum = stockNum;

		// 使用cookies模仿使用者行為 防止被偵測是使用程式抓取資料
		try {
			Response res = Jsoup.connect(infoWeb).userAgent(userAgent).execute();
			cookies = res.cookies();
		} catch (IOException e) {
			// 無此股票 將會獲取cookies失敗
			// e.printStackTrace();
			System.out.println(cookieFail);
			return null;
		}

		Stock_infoBean info = context.getBean(Stock_infoBean.class);
		info.setStockID(stockNum);
		Element body;
		try {
			Document doc = getDoc(infoWeb, buri + "q?s=" + stockNum);
			body = doc.body(); // 取出Body標籤(元素)
			// 股聘代號名稱
			Elements els = body.select("[color=#F70000]");
			for (Element e : els) {
				info.setStock_Name(e.text().substring(4, e.text().length()).trim());
				break;
			}

			// 公司type 董事長
			Elements els2 = body.select("tr[bgcolor*=#FFFFFF]");
			int count = 0;
			for (Element e : els2) {
				count += 1;
				Elements etd = e.select("td[align=left]");
				if (count == 1) {
					// System.out.println(etd.text());
					info.setBusiness_type(etd.text().trim());
					continue;
				}
				if (count == 4) {
					info.setPres_Name((etd.text().trim()));
					break;
				}
			}
			// 股本
			count = 0;
			Elements els3 = body.getElementsByClass("yui-td-left");
			for (Element e : els3) {
				count += 1;
				if (count == 3) {
					// System.out.println(e.ownText());
					info.setCapital(e.ownText().trim());
					break;
				}
			}
			// 走勢圖
			doc = getDoc(earnWeb, infoWeb);
			body = doc.body();
			Elements etd = doc.select("[width=560]");
			count = 0;
			for (Element e : etd) {
				String[] strArray = e.toString().split("\"");
				//System.out.println(Arrays.toString(strArray));
				if (count == 0) {
					info.setMon_annu_graph(getAnnu_Graph(strArray[1]));
					count++;
					continue;
				} else {
					info.setQuart_annu_graph(getAnnu_Graph(strArray[1]));
					break;
				}
			}
			info.setLast_update(new Date(new java.util.Date().getTime()));
			info.setStock_monthBeans(getStock_Monthly_Info(body, info));
			info.setStock_quartBean(getStock_Quart_Info(body, info));
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return info;
	}

	// covert Graph to byte array
	private byte[] getAnnu_Graph(String url) {

		if (url == null || url.equals(" ") || url.equalsIgnoreCase("NULL")) {
			url = noImg;
		} else {
			url = buri + url;
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
	private long str2Long(String s) {
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
	private BigDecimal str2BigDecimal(String s) {
		s = s.replace(",", "");
		s = s.replace("%", "");
		if (s.length() == 1) {
			s = s.replace("-", "0");
		}
		BigDecimal d = new BigDecimal(s.trim());

		return d;
	}
}
