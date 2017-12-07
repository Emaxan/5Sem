package by.bsuir.lab03.domain;

public class Apartment {
    private int id;
    private String hotel;
    private String room;
    private int guestId;

    public Apartment() {}

    public Apartment(int id, String hotel, String room, int guestId) {
        this.id = id;
        this.hotel = hotel;
        this.room = room;
        this.guestId = guestId;
    }

    public int getId() {
        return id;
    }

    public String getHotel() {
        return hotel;
    }

    public String getRoom() {
        return room;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel.trim();
    }

    public void setRoom(String room) {
        this.room = room.trim();
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apartment apartment = (Apartment) o;

        if (id != apartment.id) return false;
        if (guestId != apartment.guestId) return false;
        if (hotel != null ? !hotel.equals(apartment.hotel) : apartment.hotel != null) return false;
        return room != null ? room.equals(apartment.room) : apartment.room == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + guestId;
        return result;
    }

    public boolean isValid() {
        if ((hotel == null) || (hotel.length() == 0) || (hotel.length() > 50)) {
            return false;
        }
        if ((room == null) || (room.length() == 0) || (room.length() > 10)) {
            return false;
        }
        return true;
    }
}
