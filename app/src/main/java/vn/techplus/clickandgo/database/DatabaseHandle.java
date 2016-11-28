package vn.techplus.clickandgo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.techplus.clickandgo.model.Favorite;

/**
 * Created by ThanCV on 10/24/2015.
 */
public class DatabaseHandle extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "ClickAndGo";

    public static final String TABLE_FAVORITE = "Favorite";
    public static final String TABLE_RESERVATION = "Reservation";
    public static final String ID = "Id";
    public static final String USER_ID = "UserId";
    public static final String REF_TAXI = "RefTaxi";
    public static final String RESERVATION_ID = "ReservationId";
    public static final String ORIGIN = "Origin";
    public static final String DESTINATION = "Destination";
    public static final String CREATED_DATE = "CreatedDate";
    public static final String UPDATED_DATE = "UpdatedDate";

    public static final String DURATION = "Duration";
    public static final String DISTANCE = "Distance";
    public static final String DATE = "Date";
    public static final String TARI_FINAL = "TarifFinal";

    private Context context;

    //Table Favorite
    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE +
            " (" + ID + " TEXT, "
            + USER_ID + " TEXT, "
            + REF_TAXI + " TEXT, "
            + RESERVATION_ID + " TEXT, "
            + ORIGIN + " TEXT, "
            + DESTINATION + " TEXT, "
            + CREATED_DATE + " TEXT, "
            + UPDATED_DATE + " TEXT);";

    //Table Reservation
    private static final String CREATE_TABLE_RESERVATION = "CREATE TABLE " + TABLE_RESERVATION +
            " (" + ORIGIN + " TEXT, "
            + DESTINATION + " TEXT, "
            + DURATION + " TEXT, "
            + DISTANCE + " TEXT, "
            + DATE + " TEXT, "
            + TARI_FINAL + " TEXT);";

    public DatabaseHandle(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_RESERVATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);
        onCreate(db);
    }

    public void insertFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, favorite.getId());
        contentValues.put(USER_ID, favorite.getUserId());
        contentValues.put(REF_TAXI, favorite.getRefTaxi());
        contentValues.put(RESERVATION_ID, favorite.getReservationId());
        contentValues.put(ORIGIN, favorite.getOrigin());
        contentValues.put(DESTINATION, favorite.getDestination());
        contentValues.put(CREATED_DATE, favorite.getCreatedDate());
        contentValues.put(UPDATED_DATE, favorite.getUpdatedDate());

        db.insert(TABLE_FAVORITE, null, contentValues);
    }

    public void insertReservation(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ORIGIN, favorite.getOrigin());
        contentValues.put(DESTINATION, favorite.getDestination());
        contentValues.put(DURATION, favorite.getDuration());
        contentValues.put(DISTANCE, favorite.getDistance());
        contentValues.put(DATE, favorite.getDate());
        contentValues.put(TARI_FINAL, favorite.getTarifFinal());

        db.insert(TABLE_RESERVATION, null, contentValues);
    }

    //Read data table Favorite
    public List<Favorite> readDataFavorite(SQLiteDatabase db) {
        List<Favorite> list = new ArrayList<Favorite>();

        Cursor cursor = db.query(DatabaseHandle.TABLE_FAVORITE,
                new String[]{DatabaseHandle.ID,
                        DatabaseHandle.USER_ID,
                        DatabaseHandle.REF_TAXI,
                        DatabaseHandle.RESERVATION_ID,
                        DatabaseHandle.ORIGIN,
                        DatabaseHandle.DESTINATION,
                        DatabaseHandle.CREATED_DATE,
                        DatabaseHandle.UPDATED_DATE}, null, null, null, null, null);

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex(DatabaseHandle.ID);
            int userIdIndex = cursor.getColumnIndex(DatabaseHandle.USER_ID);
            int refTaxiIndex = cursor.getColumnIndex(DatabaseHandle.REF_TAXI);
            int reservationIdIndex = cursor.getColumnIndex(DatabaseHandle.RESERVATION_ID);
            int originIndex = cursor.getColumnIndex(DatabaseHandle.ORIGIN);
            int destinationIndex = cursor.getColumnIndex(DatabaseHandle.DESTINATION);
            int createdDateIndex = cursor.getColumnIndex(DatabaseHandle.CREATED_DATE);
            int updateDateIndex = cursor.getColumnIndex(DatabaseHandle.UPDATED_DATE);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(idIndex);
                String userId = cursor.getString(userIdIndex);
                String refTaxi = cursor.getString(refTaxiIndex);
                String reservationId = cursor.getString(reservationIdIndex);
                String origin = cursor.getString(originIndex);
                String destination = cursor.getString(destinationIndex);
                String createdDate = cursor.getString(createdDateIndex);
                String updateDate = cursor.getString(updateDateIndex);
                list.add(new Favorite(id, userId, refTaxi, reservationId, origin, destination, createdDate, updateDate));
                cursor.moveToNext();
            }
        }

        return list;
    }

    //Read data table Reservation
    public List<Favorite> readDataReservation(SQLiteDatabase db) {
        List<Favorite> list = new ArrayList<Favorite>();

        Cursor cursor = db.query(DatabaseHandle.TABLE_RESERVATION,
                new String[]{DatabaseHandle.ORIGIN,
                        DatabaseHandle.DESTINATION,
                        DatabaseHandle.DURATION,
                        DatabaseHandle.DISTANCE,
                        DatabaseHandle.DATE,
                        DatabaseHandle.TARI_FINAL}, null, null, null, null, null);

        if (cursor != null) {
            int originIndex = cursor.getColumnIndex(DatabaseHandle.ORIGIN);
            int destinationIndex = cursor.getColumnIndex(DatabaseHandle.DESTINATION);
            int durationIndex = cursor.getColumnIndex(DatabaseHandle.DURATION);
            int distanceIndex = cursor.getColumnIndex(DatabaseHandle.DISTANCE);
            int dateIndex = cursor.getColumnIndex(DatabaseHandle.DATE);
            int tariFinalIndex = cursor.getColumnIndex(DatabaseHandle.TARI_FINAL);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String origin = cursor.getString(originIndex);
                String destination = cursor.getString(destinationIndex);
                String duration = cursor.getString(durationIndex);
                String distance = cursor.getString(distanceIndex);
                String date = cursor.getString(dateIndex);
                String tariFinal = cursor.getString(tariFinalIndex);
                list.add(new Favorite(origin, destination, duration, distance, date, tariFinal));
                cursor.moveToNext();
            }
        }

        return list;
    }

    public void deleteDataFavorite(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_FAVORITE);
    }
    public void deleteDataReservation(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_RESERVATION);
    }
}
