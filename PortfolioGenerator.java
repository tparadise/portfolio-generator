import java.math.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;

public class PortfolioGenerator {
    /* Generates a portfolio of stocks randomly weighted by Market Cap.
      
       Theory: Mimics an indexed fund such as those of the S&P 500 without the inneficiencies
           associated with purchasing large volumes of stock added to the market index. Prices are
           inflated artifically when the market reacts to an addition to the S&P 500 or similar market
           indexes. This portfolio attempts to avoid those price inflations while still catching the
	   average growth of the market.

       Strategy: 5+ year holding time. Invest in stocks contained in the portfolio equally. 

       Goal: Return equivalent to twice that of the S&P 500 over the same time period.

       Input: CSV file of considered stocks. Suggestions include full exchanges such as the NASDAQ, NYSE,
           or both, but it can be applied to targeted industries.

       Process: Will create Stock objects from input data and randomly select a portfolio weighted by
           market cap to capture the entire market while mitigated higher risks associated with small cap stocks.
      
       Output: Portfolio of 20 stocks.
    */

    public static void main(String[] args) {
	PortfolioGenerator pg = new PortfolioGenerator();
	pg.generate();
    }
    
    public void generate() {
	/* Might want to seperate readCVS and createExchange functions
	   1. Reads cvs file. creates Stock objects and puts them into ArrayList exchange - createExchange()
	   2. Randomly chooses stocks wieghted by market cap - chooseRandomStock()
	   3. Prints list of chosen stocks - internal
	*/
	Scanner in = new Scanner(System.in);
	System.out.println("Enter name of exchange (ex. 'nasdaq'): ");
	String csvFile = in.next();
	csvFile += ".csv";
	ArrayList<Stock> exchange = createExchange(csvFile);
	ArrayList<Stock> portfolio = new ArrayList<Stock>();
	System.out.println("Ticker   :   Market Cap   :   Company");
	for (int i = 0; i < 20; i++) {
	    Stock stock = chooseRandomStock(exchange);
	    if (portfolio.contains(stock)) {
		i--;
	    } else {
		portfolio.add(stock);
		System.out.println(stock.getTicker() + "  :  " + stock.getMarketCap() + "  :  " + stock.getName());
	    }
	}
    }

    public ArrayList<Stock> createExchange(String csvFile) {
	// Reads cvs file of stock tickers and market caps and creates stock objects containing corresponding information.
	// Returns ArrayList exchange of stock objects.
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	ArrayList<Stock> exchange = new ArrayList<Stock>();
 
	try {
 
	    br = new BufferedReader(new FileReader(csvFile));
	    while ((line = br.readLine()) != null) {
 
		String[] stockLine = line.split(cvsSplitBy);
 
		Stock nextStock = new Stock();
		nextStock.setTicker(stockLine[0]);
		// System.out.println(stockLine[0]);
		double marketCap = Double.parseDouble(stockLine[1]);
		// System.out.println(stockLine[1]);
		nextStock.setMarketCap(marketCap);
		nextStock.setName(stockLine[2]);
		
		exchange.add(nextStock);
	    }
 
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
       	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}	
	System.out.println("Exchange Created");
	return exchange;
    }

    public Stock chooseRandomStock(ArrayList<Stock> exchange) {
	// Chooses random stock weighted by market cap.
        double totalMarketCap = 0.0;
        for (Stock stock : exchange) {
            totalMarketCap += stock.getMarketCap();
	}       
	double chooser = Math.random() * totalMarketCap;
        double countWeight = 0.0;
        for (Stock stock : exchange) {
            countWeight += stock.getMarketCap();
            if (countWeight >= chooser)
                return stock;
        }
	return null;
    } 
}
