package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.exceptions.DomainException;

public class Reservation {
    private Integer roomNumber;
    private Date checkIn;
    private Date checkOut;

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Reservation(Integer roomNumber, Date checkIn, Date checkOut) {
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {
        return this.checkIn;
    }

    public Date getCheckOut() {
        return this.checkOut;
    }

    public long duration() {
        long diff = this.checkOut.getTime() - this.checkIn.getTime();
        TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return diff;
    }

    public void updateDates(Date checkIn, Date checkOut) throws DomainException {
        if (checkIn.before(new Date()) || checkOut.before(new Date())) {
            throw new DomainException("Reservation dates for update must be future dates");
        }
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Room " + this.roomNumber + ", check-in: " + sdf.format(this.checkIn) + ", check-out: "
                + sdf.format(this.checkOut) + ", " + sdf.format(this.duration()) + " nights";
    }
}
