////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.bll.dto;

/**
 * Book data transfer object.
 */
public class BookDto {
    /**
     * Book name.
     */
    private String name;
    /**
     * Author of this book.
     */
    private String author;
    /**
     * Year of writing book.
     */
    private int year;

    /**
     * Get name of this book.
     *
     * @return Name of this book.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of this book.
     *
     * @param n Name of this book.
     */
    public void setName(final String n) {
        this.name = n;
    }

    /**
     * Get author of this book.
     *
     * @return Author of this book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set author of this book.
     *
     * @param a Author of this book.
     */
    public void setAuthor(final String a) {
        this.author = a;
    }

    /**
     * Get year of publishing of this book.
     *
     * @return Year of publishing of this book.
     */
    public int getYear() {
        return year;
    }

    /**
     * Set year of publishing of this book.
     *
     * @param y Year of publishing of this book.
     */
    public void setYear(final int y) {
        this.year = y;
    }

}
