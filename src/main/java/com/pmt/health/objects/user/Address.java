package com.pmt.health.objects.user;

public class Address {

    private String addressLineOne = null;
    private String addressLineTwo = "";
    private String city = null;
    private State state = State.ALABAMA;
    private String zip = null;
    private String country = "USA";

    /**
     * Default constructor, setting all address information to null, and states to Alabama and Arizona
     */
    public Address() {
    }

    /**
     * Sets the provided address, city and zip, but sets the rest to null. Country is set to USA
     *
     * @param addressLineOne - the first line of the address
     * @param city           - the city of the address
     * @param zip            - the zip of the address
     */
    public Address(String addressLineOne, String city, String zip) {
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = "";
        this.city = city;
        this.state = null;
        this.zip = zip;
    }

    /**
     * If a specific first line of the address is set, it is returned, otherwise, the default line one address of the
     * state is returned
     *
     * @return the first line of the address
     */
    public String getAddressLineOne() {
        if (addressLineOne == null) {
            return state.getAddress().getAddressLineOne();
        }
        return addressLineOne;
    }

    public void setAddressLineOne(String addressLineOne) {
        this.addressLineOne = addressLineOne;
    }

    /**
     * If a specific second line of the address is set, it is returned, otherwise, the default line two address of the
     * state is returned
     *
     * @return the second line of the address
     */
    public String getAddressLineTwo() {
        if (addressLineTwo == null) {
            return state.getAddress().getAddressLineTwo();
        }
        return addressLineTwo;
    }

    public void setAddressLineTwo(String addressLineTwo) {
        this.addressLineTwo = addressLineTwo;
    }

    /**
     * If a specific city is set, it is returned, otherwise, the default city of the
     * state is returned
     *
     * @return the city
     */
    public String getCity() {
        if (city == null) {
            return state.getAddress().getCity();
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public String resetStateAndGetAbbreviation(State newState) {
        setState(newState);
        return state.getAbbreviation();
    }

    public void setState(State state) {
        this.state = state;
    }

    /**
     * If a specific zip is set, it is returned, otherwise, the default zip of the
     * state is returned
     *
     * @return the zipcode
     */
    public String getZip() {
        if (zip == null) {
            return state.getAddress().getZip();
        }
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * If a specific country is set, it is returned, otherwise, the default country of the
     * state is returned
     *
     * @return the country
     */
    public String getCountry() {
        if (country == null) {
            return state.getAddress().country;
        }
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
