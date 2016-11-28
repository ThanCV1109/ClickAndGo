
package vn.techplus.clickandgo.model;

public class Favorite {

    private String id;
    private String userId;
    private String refTaxi;
    private String reservationId;
    private String origin;
    private String destination;
    private String createdDate;
    private String updatedDate;
    private String duration;
    private String distance;
    private String date;
    private String tarifFinal;

    public Favorite() {
    }

    public Favorite(String id, String userId, String refTaxi, String reservationId, String origin, String destination, String createdDate, String updatedDate) {
        this.id = id;
        this.userId = userId;
        this.refTaxi = refTaxi;
        this.reservationId = reservationId;
        this.origin = origin;
        this.destination = destination;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Favorite(String origin, String destination, String duration, String distance, String date, String tarifFinal) {
        this.origin = origin;
        this.destination = destination;
        this.duration = duration;
        this.distance = distance;
        this.date = date;
        this.tarifFinal = tarifFinal;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The refTaxi
     */
    public String getRefTaxi() {
        return refTaxi;
    }

    /**
     * @param refTaxi The ref_taxi
     */
    public void setRefTaxi(String refTaxi) {
        this.refTaxi = refTaxi;
    }

    /**
     * @return The reservationId
     */
    public String getReservationId() {
        return reservationId;
    }

    /**
     * @param reservationId The reservation_id
     */
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * @return The origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin The origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return The destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return The updatedDate
     */
    public String getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate The updated_date
     */
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTarifFinal() {
        return tarifFinal;
    }

    public void setTarifFinal(String tarifFinal) {
        this.tarifFinal = tarifFinal;
    }

}
