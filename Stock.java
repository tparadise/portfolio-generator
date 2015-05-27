public class Stock {
    // Stocks have ticker and marketCap. Could be expanded to include other information.                                                                                         
    private String ticker;
    private double marketCap;
    private String name;

    public String getTicker() {
	return ticker;
    }

    public void setTicker(String t) {
	ticker = t;
    }

    public double getMarketCap() {
	return marketCap;
    }

    public void setMarketCap(double mc) {
	marketCap = mc;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }
}
