package za.co.android.nihapp.Model;

public class BillSummaryModel {
    private long ParentId;
    private int NumberOfTrips;
    private Double TotalCost;
    private Double RatePerTrip;
    private String From;
    private String To;

    public BillSummaryModel() {
    }

    public BillSummaryModel(long parentId, int numberOfTrips, Double totalCost, Double ratePerTrip, String from, String to) {
        ParentId = parentId;
        NumberOfTrips = numberOfTrips;
        TotalCost = totalCost;
        RatePerTrip = ratePerTrip;
        From = from;
        To = to;
    }

    public long getParentId() {
        return ParentId;
    }

    public void setParentId(long parentId) {
        ParentId = parentId;
    }

    public int getNumberOfTrips() {
        return NumberOfTrips;
    }

    public void setNumberOfTrips(int numberOfTrips) {
        NumberOfTrips = numberOfTrips;
    }

    public Double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(Double totalCost) {
        TotalCost = totalCost;
    }

    public Double getRatePerTrip() {
        return RatePerTrip;
    }

    public void setRatePerTrip(Double ratePerTrip) {
        RatePerTrip = ratePerTrip;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    @Override
    public String toString() {
        return "BillSummaryModel{" +
                "ParentId=" + ParentId +
                ", NumberOfTrips=" + NumberOfTrips +
                ", TotalCost=" + TotalCost +
                ", RatePerTrip=" + RatePerTrip +
                ", From='" + From + '\'' +
                ", To='" + To + '\'' +
                '}';
    }
}
