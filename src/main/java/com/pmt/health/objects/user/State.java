package com.pmt.health.objects.user;

import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.Map;

public enum State implements UserValue {

    ALABAMA("Alabama", "AL", new Address("1740 Carter Hill Rd", "Montgomery", "36106"), "938", true),
    ALASKA("Alaska", "AK", new Address("3101 A St", "Anchorage", "99503"), "907", false),
    AMERICAN_SAMOA("American Samoa", "AS", new Address("Tafuna", "Western District", "96799"), "684", false),
    ARIZONA("Arizona", "AZ", new Address("461 S Bryant Ave", "Tucson", "85711"), "480", true),
    ARKANSAS("Arkansas", "AR", new Address("2302 College Ave", "Conway", "72034"), "501", false),
    CALIFORNIA("California", "CA", new Address("424 Octavia St", "San Francisco", "94102"), "949", true),
    COLORADO("Colorado", "CO", new Address("2032 14th St", "Boulder", "80302"), "970", true),
    CONNECTICUT("Connecticut", "CT", new Address("114 Woodland St", "Hartford", "06105"), "203", true),
    DELAWARE("Delaware", "DE", new Address("501 W 14th St", "Wilmington", "19801"), "302", false),
    DISTRICT_OF_COLUMBIA("District of Columbia", "DC", new Address("1600 Pennsylvania Ave NW", "Washington", "20500"), "202",
            false),
    FEDERATED_STATES_OF_MICRONESIA("Federated States of Micronesia", "FM", new Address(), "999", false),
    FLORIDA("Florida", "FL", new Address("3501 SW 8th St", "Miami", "33135"), "239", true),
    GEORGIA("Georgia", "GA", new Address("1670 Clairmont Rd", "Decatur", "30033"), "229", true),
    GUAM("Guam", "GU", new Address("Bldg #50 Farenholt Ave", "Tutuhan", "96910"), "671", false),
    HAWAII("Hawaii", "HI", new Address("1301 Punchbowl St", "Honolulu", "96813"), "808", false),
    IDAHO("Idaho", "ID", new Address("2100 12th Ave Rd", "Nampa", "83686"), "208", false),
    ILLINOIS("Illinois", "IL", new Address("2607 W 17th St", "Chicago", "60608"), "217", true),
    INDIANA("Indiana", "IN", new Address("308 W 4th St", "Bloomington", "47404"), "219", false),
    IOWA("Iowa", "IA", new Address("315 E 4th St", "Waterloo", "50703"), "319", false),
    KANSAS("Kansas", "KS", new Address("1845 Fairmount St", "Wichita", "67260"), "620", false),
    KENTUCKY("Kentucky", "KY", new Address("309 8th St", "Shelbyville", "40065"), "859", false),
    LOUISIANA("Louisiana", "LA", new Address("1438 MacArthur Dr", "Alexandria", "70130"), "225", false),
    MAINE("Maine", "ME", new Address("74 Main St", "Bangor", "04401"), "207", true),
    MARYLAND("Maryland", "MD", new Address("10101 River Rd", "Potomac", "20854"), "240", false),
    MARSHALL_ISLANDS("Marshall Islands", "MH", new Address("Roi-Namur", "Kwajalein Atoll", "96970"), "999", false),
    MASSACHUSETTS("Massachusetts", "MA", new Address("415 Bryant Road", "Boston", "02132"), "339", true),
    MICHIGAN("Michigan", "MI", new Address("2205 Jefferson Ave", "Midland", "48640"), "517", true),
    MINNESOTA("Minnesota", "MN", new Address("1501 Hennepin Ave", "Minneapolis", "55403"), "612", true),
    MISSISSIPPI("Mississippi", "MS", new Address("636 Mississippi River Blvd", "St Paul", "55116"), "662", true),
    MISSOURI("Missouri", "MO", new Address("400 W 11th St", "Rolla", "65409"), "816", false),
    MONTANA("Montana", "MT", new Address("405 W Garfield St", "Bozeman", "59715"), "406", false),
    NEBRASKA("Nebraska", "NE", new Address("218 E 8th St", "Cozad", "69130"), "308", false),
    NEVADA("Nevada", "NV", new Address("987 College Ave", "Elko", "89801"), "702", false),
    NEW_HAMPSHIRE("New Hampshire", "NH", new Address("38 N Main St", "Concord", "03301"), "603", true),
    NEW_JERSEY("New Jersey", "NJ", new Address("81 Plainfield Ave", "Edison", "08817"), "609", true),
    NEW_MEXICO("New Mexico", "NM", new Address("1002 W Richardson Ave", "Artesia", "88210"), "505", false),
    NEW_YORK("New York", "NY", new Address("222 Madison Ave", "Albany", "12230"), "516", true),
    NORTH_CAROLINA("North Carolina", "NC", new Address("601 S College Rd", "Wilmington", "28403"), "704", false),
    NORTH_DAKOTA("North Dakota", "ND", new Address("612 E Boulevard Ave", "Bismarck", "58505"), "701", true),
    NORTHERN_MARIANA_ISLANDS("Northern Mariana Islands", "MP", new Address("Afetna", "Saipan", "96950"), "999", false),
    OHIO("Ohio", "OH", new Address("1330 E Livingston Ave", "Columbus", "43205"), "216", false),
    OKLAHOMA("Oklahoma", "OK", new Address("7777 S Lewis Ave", "Tulsa", "74171"), "405", false),
    OREGON("Oregon", "OR", new Address("1430 Johnson Lane", "Eugene", "97403"), "458", false),
    PALAU("Palau", "PW", new Address("Belvedere Apartments", "Main St", "96940"), "999", false),
    PENNSYLVANIA("Pennsylvania", "PA", new Address("300 Highland Ave", "Hanover", "17331"), "215", false),
    PUERTO_RICO("Puerto Rico", "PR", new Address("Parque Batey Central", "Calle Unión", "00738"), "787", false),
    RHODE_ISLAND("Rhode Island", "RI", new Address("5 Touro Park St W", "Newport", "02840"), "401", true),
    SOUTH_CAROLINA("South Carolina", "SC", new Address("801 Lincoln St", "Columbia", "29201"), "803", true),
    SOUTH_DAKOTA("South Dakota", "SD", new Address("421 5th Ave NW", "Watertown", "57201"), "605", false),
    TENNESSEE("Tennessee", "TN", new Address("545 Lane Ave", "Jackson", "38301"), "423", true),
    TEXAS("Texas", "TX", new Address("1201 Franklin St", "Houston", "75201"), "430", true),
    UTAH("Utah", "UT", new Address("665 Center St", "Orem", "84057"), "435", false),
    VERMONT("Vermont", "VT", new Address("11 Center St", "Rutland", "05701"), "802", true),
    VIRGIN_ISLANDS("Virgin Islands", "VI", new Address("Route 30", "Charlotte Amalie", "00802"), "340", false),
    VIRGINIA("Virginia", "VA", new Address("5035 Sideburn Rd", "Fairfax", "22032"), "434", false),
    WASHINGTON("Washington", "WA", new Address("2020 22nd Ave SE", "Olympia", "98501"), "509", false),
    WEST_VIRGINIA("West Virginia", "WV", new Address("200 Civic Center Dr", "Charleston", "25301"), "681", false),
    WISCONSIN("Wisconsin", "WI", new Address("2 E Main St", "Madison", "53703"), "262", true),
    WYOMING("Wyoming", "WY", new Address("400 E Collins Dr", "Casper", "82601"), "307", false),
    UNKNOWN("Unknown", "", new Address(), "999", false);

    /**
     * The set of states addressed by abbreviations.
     */
    private static final Map<String, State> STATES_BY_ABBR = new HashMap<>();
    private static final Map<String, State> STATES_BY_AREA_CODE = new HashMap<>();
    private static final Logger log = Logger.getLogger(State.class);

    /* static initializer */
    static {
        for (State state : values()) {
            STATES_BY_ABBR.put(state.getAbbreviation(), state);
        }
    }

    /* static initializer */
    static {
        for (State state : values()) {
            STATES_BY_AREA_CODE.put(state.getValidAreaCode(), state);
        }
    }

    /**
     * The state's name.
     */
    private String name;
    /**
     * The state's abbreviation.
     */
    private String abbreviation;
    /**
     * The default address associated with the state residence
     */
    private Address address;
    /**
     * Does the state have any associated site pairings
     */
    private Boolean sitePairing;
    /**
     * Valid area code used for valid phone number generation
     */
    private String validAreaCode;

    /**
     * Constructs a new state.
     *
     * @param name         the state's name.
     * @param abbreviation the state's abbreviation.
     * @param sitePairing  whether or not there are hpos associated with the state
     */
    State(String name, String abbreviation, Address address, String validAreaCode, Boolean sitePairing) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.address = address;
        this.validAreaCode = validAreaCode;
        this.sitePairing = sitePairing;
    }

    /**
     * Gets the enum constant with the specified abbreviation.
     *
     * @param abbr the state's abbreviation.
     * @return the enum constant with the specified abbreviation.
     */
    public static State valueOfAbbreviation(final String abbr) {
        final State state = STATES_BY_ABBR.get(abbr);
        if (state != null) {
            return state;
        } else {
            return UNKNOWN;
        }
    }

    /**
     * Gets the enum constant with the specified area code.
     *
     * @param areaCode the state's area code.
     * @return the enum constant with the specified abbreviation.
     */
    public static State valueOfAreaCode(final String areaCode) {
        final State state = STATES_BY_AREA_CODE.get(areaCode);
        if (state != null) {
            return state;
        } else {
            return UNKNOWN;
        }
    }

    public static State valueOfName(final String name) {
        final String enumName = name.toUpperCase().replaceAll(" ", "_");
        try {
            return valueOf(enumName);
        } catch (final IllegalArgumentException e) {
            log.warn("Unable to define the state in question. " + e);
            return State.UNKNOWN;
        }
    }

    /**
     * Returns the state's abbreviation.
     *
     * @return the state's abbreviation.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Returns the state's default address
     *
     * @return the state's default address
     */
    public Address getAddress() {
        return address;
    }

    public Boolean getSitePairing() {
        return sitePairing;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getValue() {
        return name;
    }

    public String getAPIValue(String prefix) {
        return prefix + "_" + abbreviation;
    }

    public String getValidAreaCode() {
        return validAreaCode;
    }
}
