package vn.techplus.clickandgo.model;

/**
 * Created by ThanCV on 11/9/2015.
 */
public class AddressAutoComplete {
    private String placeId;
    private String addressName;

    public AddressAutoComplete() {
    }

    public AddressAutoComplete(String addressName, String placeId) {
        this.addressName = addressName;
        this.placeId = placeId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}
