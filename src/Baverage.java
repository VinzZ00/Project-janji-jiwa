
public class Baverage {

	private String baverageId, baverageName, baverageType;
	private int baveragePrice, baverageStock;
	
	public Baverage(String baverageId, String baverageName, String baverageType, int baveragePrice, int baverageStock) {
		super();
		this.baverageId = baverageId;
		this.baverageName = baverageName;
		this.baverageType = baverageType;
		this.baveragePrice = baveragePrice;
		this.baverageStock = baverageStock;
	}

	public String getBaverageId() {
		return baverageId;
	}

	public void setBaverageId(String baverageId) {
		this.baverageId = baverageId;
	}

	public String getBaverageName() {
		return baverageName;
	}

	public void setBaverageName(String baverageName) {
		this.baverageName = baverageName;
	}

	public String getBaverageType() {
		return baverageType;
	}

	public void setBaverageType(String baverageType) {
		this.baverageType = baverageType;
	}

	public int getBaveragePrice() {
		return baveragePrice;
	}

	public void setBaveragePrice(int baveragePrice) {
		this.baveragePrice = baveragePrice;
	}

	public int getBaverageStock() {
		return baverageStock;
	}

	public void setBaverageStock(int baverageStock) {
		this.baverageStock = baverageStock;
	}
	
	

}
