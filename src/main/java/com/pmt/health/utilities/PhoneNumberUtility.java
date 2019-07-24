package com.pmt.health.utilities;

import com.pmt.health.objects.user.State;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PhoneNumberUtility {

    private static final ConcurrentHashMap<String, List<String>> validAreaAndRegionCodes = new ConcurrentHashMap<>();

    private static Random rand = new Random();
    private static State[] areaCodeArray = new State[1000];

    static {
        areaCodeArray[201] = State.NEW_JERSEY;
        areaCodeArray[202] = State.DISTRICT_OF_COLUMBIA;
        areaCodeArray[203] = State.CONNECTICUT;
        areaCodeArray[205] = State.ALABAMA;
        areaCodeArray[206] = State.WASHINGTON;
        areaCodeArray[207] = State.MAINE;
        areaCodeArray[208] = State.IDAHO;
        areaCodeArray[209] = State.CALIFORNIA;
        areaCodeArray[210] = State.TEXAS;
        areaCodeArray[212] = State.NEW_YORK;
        areaCodeArray[213] = State.CALIFORNIA;
        areaCodeArray[214] = State.TEXAS;
        areaCodeArray[215] = State.PENNSYLVANIA;
        areaCodeArray[216] = State.OHIO;
        areaCodeArray[217] = State.ILLINOIS;
        areaCodeArray[218] = State.MINNESOTA;
        areaCodeArray[219] = State.INDIANA;
        areaCodeArray[224] = State.ILLINOIS;
        areaCodeArray[225] = State.LOUISIANA;
        areaCodeArray[228] = State.MISSISSIPPI;
        areaCodeArray[229] = State.GEORGIA;
        areaCodeArray[231] = State.MICHIGAN;
        areaCodeArray[234] = State.OHIO;
        areaCodeArray[239] = State.FLORIDA;
        areaCodeArray[240] = State.MARYLAND;
        areaCodeArray[248] = State.MICHIGAN;
        areaCodeArray[251] = State.ALABAMA;
        areaCodeArray[252] = State.NORTH_CAROLINA;
        areaCodeArray[253] = State.WASHINGTON;
        areaCodeArray[254] = State.TEXAS;
        areaCodeArray[256] = State.ALABAMA;
        areaCodeArray[260] = State.INDIANA;
        areaCodeArray[262] = State.WISCONSIN;
        areaCodeArray[267] = State.PENNSYLVANIA;
        areaCodeArray[269] = State.MICHIGAN;
        areaCodeArray[270] = State.KENTUCKY;
        areaCodeArray[276] = State.VIRGINIA;
        areaCodeArray[281] = State.TEXAS;
        areaCodeArray[301] = State.MARYLAND;
        areaCodeArray[302] = State.DELAWARE;
        areaCodeArray[303] = State.COLORADO;
        areaCodeArray[304] = State.WEST_VIRGINIA;
        areaCodeArray[305] = State.FLORIDA;
        areaCodeArray[307] = State.WYOMING;
        areaCodeArray[308] = State.NEBRASKA;
        areaCodeArray[309] = State.ILLINOIS;
        areaCodeArray[310] = State.CALIFORNIA;
        areaCodeArray[312] = State.ILLINOIS;
        areaCodeArray[313] = State.MICHIGAN;
        areaCodeArray[314] = State.MISSOURI;
        areaCodeArray[315] = State.NEW_YORK;
        areaCodeArray[316] = State.KANSAS;
        areaCodeArray[317] = State.INDIANA;
        areaCodeArray[318] = State.LOUISIANA;
        areaCodeArray[319] = State.IOWA;
        areaCodeArray[320] = State.MINNESOTA;
        areaCodeArray[321] = State.FLORIDA;
        areaCodeArray[323] = State.CALIFORNIA;
        areaCodeArray[325] = State.TEXAS;
        areaCodeArray[330] = State.OHIO;
        areaCodeArray[331] = State.ILLINOIS;
        areaCodeArray[334] = State.ALABAMA;
        areaCodeArray[336] = State.NORTH_CAROLINA;
        areaCodeArray[337] = State.LOUISIANA;
        areaCodeArray[339] = State.MASSACHUSETTS;
        areaCodeArray[340] = State.VIRGIN_ISLANDS;
        areaCodeArray[347] = State.NEW_YORK;
        areaCodeArray[351] = State.MASSACHUSETTS;
        areaCodeArray[352] = State.FLORIDA;
        areaCodeArray[360] = State.WASHINGTON;
        areaCodeArray[361] = State.TEXAS;
        areaCodeArray[385] = State.UTAH;
        areaCodeArray[386] = State.FLORIDA;
        areaCodeArray[401] = State.RHODE_ISLAND;
        areaCodeArray[402] = State.NEBRASKA;
        areaCodeArray[404] = State.GEORGIA;
        areaCodeArray[405] = State.OKLAHOMA;
        areaCodeArray[406] = State.MONTANA;
        areaCodeArray[407] = State.FLORIDA;
        areaCodeArray[408] = State.CALIFORNIA;
        areaCodeArray[409] = State.TEXAS;
        areaCodeArray[410] = State.MARYLAND;
        areaCodeArray[412] = State.PENNSYLVANIA;
        areaCodeArray[413] = State.MASSACHUSETTS;
        areaCodeArray[414] = State.WISCONSIN;
        areaCodeArray[415] = State.CALIFORNIA;
        areaCodeArray[417] = State.MISSOURI;
        areaCodeArray[419] = State.OHIO;
        areaCodeArray[423] = State.TENNESSEE;
        areaCodeArray[424] = State.CALIFORNIA;
        areaCodeArray[425] = State.WASHINGTON;
        areaCodeArray[430] = State.TEXAS;
        areaCodeArray[432] = State.TEXAS;
        areaCodeArray[434] = State.VIRGINIA;
        areaCodeArray[435] = State.UTAH;
        areaCodeArray[440] = State.OHIO;
        areaCodeArray[442] = State.CALIFORNIA;
        areaCodeArray[443] = State.MARYLAND;
        areaCodeArray[458] = State.OREGON;
        areaCodeArray[469] = State.TEXAS;
        areaCodeArray[470] = State.GEORGIA;
        areaCodeArray[475] = State.CONNECTICUT;
        areaCodeArray[478] = State.GEORGIA;
        areaCodeArray[479] = State.ARKANSAS;
        areaCodeArray[480] = State.ARIZONA;
        areaCodeArray[484] = State.PENNSYLVANIA;
        areaCodeArray[501] = State.ARKANSAS;
        areaCodeArray[502] = State.KENTUCKY;
        areaCodeArray[503] = State.OREGON;
        areaCodeArray[504] = State.LOUISIANA;
        areaCodeArray[505] = State.NEW_MEXICO;
        areaCodeArray[507] = State.MINNESOTA;
        areaCodeArray[508] = State.MASSACHUSETTS;
        areaCodeArray[509] = State.WASHINGTON;
        areaCodeArray[510] = State.CALIFORNIA;
        areaCodeArray[512] = State.TEXAS;
        areaCodeArray[513] = State.OHIO;
        areaCodeArray[515] = State.IOWA;
        areaCodeArray[516] = State.NEW_YORK;
        areaCodeArray[517] = State.MICHIGAN;
        areaCodeArray[518] = State.NEW_YORK;
        areaCodeArray[520] = State.ARIZONA;
        areaCodeArray[530] = State.CALIFORNIA;
        areaCodeArray[539] = State.OKLAHOMA;
        areaCodeArray[540] = State.VIRGINIA;
        areaCodeArray[541] = State.OREGON;
        areaCodeArray[551] = State.NEW_JERSEY;
        areaCodeArray[559] = State.CALIFORNIA;
        areaCodeArray[561] = State.FLORIDA;
        areaCodeArray[562] = State.CALIFORNIA;
        areaCodeArray[563] = State.IOWA;
        areaCodeArray[567] = State.OHIO;
        areaCodeArray[570] = State.PENNSYLVANIA;
        areaCodeArray[571] = State.VIRGINIA;
        areaCodeArray[573] = State.MISSOURI;
        areaCodeArray[574] = State.INDIANA;
        areaCodeArray[575] = State.NEW_MEXICO;
        areaCodeArray[580] = State.OKLAHOMA;
        areaCodeArray[585] = State.NEW_YORK;
        areaCodeArray[586] = State.MICHIGAN;
        areaCodeArray[601] = State.MISSISSIPPI;
        areaCodeArray[602] = State.ARIZONA;
        areaCodeArray[603] = State.NEW_HAMPSHIRE;
        areaCodeArray[605] = State.SOUTH_DAKOTA;
        areaCodeArray[606] = State.KENTUCKY;
        areaCodeArray[607] = State.NEW_YORK;
        areaCodeArray[608] = State.WISCONSIN;
        areaCodeArray[609] = State.NEW_JERSEY;
        areaCodeArray[610] = State.PENNSYLVANIA;
        areaCodeArray[612] = State.MINNESOTA;
        areaCodeArray[614] = State.OHIO;
        areaCodeArray[615] = State.TENNESSEE;
        areaCodeArray[616] = State.MICHIGAN;
        areaCodeArray[617] = State.MASSACHUSETTS;
        areaCodeArray[618] = State.ILLINOIS;
        areaCodeArray[619] = State.CALIFORNIA;
        areaCodeArray[620] = State.KANSAS;
        areaCodeArray[623] = State.ARIZONA;
        areaCodeArray[626] = State.CALIFORNIA;
        areaCodeArray[630] = State.ILLINOIS;
        areaCodeArray[631] = State.NEW_YORK;
        areaCodeArray[636] = State.MISSOURI;
        areaCodeArray[641] = State.IOWA;
        areaCodeArray[646] = State.NEW_YORK;
        areaCodeArray[650] = State.CALIFORNIA;
        areaCodeArray[651] = State.MINNESOTA;
        areaCodeArray[657] = State.CALIFORNIA;
        areaCodeArray[660] = State.MISSOURI;
        areaCodeArray[661] = State.CALIFORNIA;
        areaCodeArray[662] = State.MISSISSIPPI;
        areaCodeArray[671] = State.GUAM;
        areaCodeArray[678] = State.GEORGIA;
        areaCodeArray[681] = State.WEST_VIRGINIA;
        areaCodeArray[682] = State.TEXAS;
        areaCodeArray[684] = State.AMERICAN_SAMOA;
        areaCodeArray[701] = State.NORTH_DAKOTA;
        areaCodeArray[702] = State.NEVADA;
        areaCodeArray[703] = State.VIRGINIA;
        areaCodeArray[704] = State.NORTH_CAROLINA;
        areaCodeArray[706] = State.GEORGIA;
        areaCodeArray[707] = State.CALIFORNIA;
        areaCodeArray[708] = State.ILLINOIS;
        areaCodeArray[712] = State.IOWA;
        areaCodeArray[713] = State.TEXAS;
        areaCodeArray[714] = State.CALIFORNIA;
        areaCodeArray[715] = State.WISCONSIN;
        areaCodeArray[716] = State.NEW_YORK;
        areaCodeArray[717] = State.PENNSYLVANIA;
        areaCodeArray[718] = State.NEW_YORK;
        areaCodeArray[719] = State.COLORADO;
        areaCodeArray[720] = State.COLORADO;
        areaCodeArray[724] = State.PENNSYLVANIA;
        areaCodeArray[727] = State.FLORIDA;
        areaCodeArray[731] = State.TENNESSEE;
        areaCodeArray[732] = State.NEW_JERSEY;
        areaCodeArray[734] = State.MICHIGAN;
        areaCodeArray[740] = State.OHIO;
        areaCodeArray[747] = State.CALIFORNIA;
        areaCodeArray[754] = State.FLORIDA;
        areaCodeArray[757] = State.VIRGINIA;
        areaCodeArray[760] = State.CALIFORNIA;
        areaCodeArray[762] = State.GEORGIA;
        areaCodeArray[763] = State.MINNESOTA;
        areaCodeArray[765] = State.INDIANA;
        areaCodeArray[769] = State.MISSISSIPPI;
        areaCodeArray[770] = State.GEORGIA;
        areaCodeArray[772] = State.FLORIDA;
        areaCodeArray[773] = State.ILLINOIS;
        areaCodeArray[774] = State.MASSACHUSETTS;
        areaCodeArray[775] = State.NEVADA;
        areaCodeArray[779] = State.ILLINOIS;
        areaCodeArray[781] = State.MASSACHUSETTS;
        areaCodeArray[785] = State.KANSAS;
        areaCodeArray[786] = State.FLORIDA;
        areaCodeArray[787] = State.PUERTO_RICO;
        areaCodeArray[801] = State.UTAH;
        areaCodeArray[802] = State.VERMONT;
        areaCodeArray[803] = State.SOUTH_CAROLINA;
        areaCodeArray[804] = State.VIRGINIA;
        areaCodeArray[805] = State.CALIFORNIA;
        areaCodeArray[806] = State.TEXAS;
        areaCodeArray[808] = State.HAWAII;
        areaCodeArray[810] = State.MICHIGAN;
        areaCodeArray[812] = State.INDIANA;
        areaCodeArray[813] = State.FLORIDA;
        areaCodeArray[814] = State.PENNSYLVANIA;
        areaCodeArray[815] = State.ILLINOIS;
        areaCodeArray[816] = State.MISSOURI;
        areaCodeArray[817] = State.TEXAS;
        areaCodeArray[818] = State.CALIFORNIA;
        areaCodeArray[828] = State.NORTH_CAROLINA;
        areaCodeArray[830] = State.TEXAS;
        areaCodeArray[831] = State.CALIFORNIA;
        areaCodeArray[832] = State.TEXAS;
        areaCodeArray[843] = State.SOUTH_CAROLINA;
        areaCodeArray[845] = State.NEW_YORK;
        areaCodeArray[847] = State.ILLINOIS;
        areaCodeArray[848] = State.NEW_JERSEY;
        areaCodeArray[850] = State.FLORIDA;
        areaCodeArray[856] = State.NEW_JERSEY;
        areaCodeArray[857] = State.MASSACHUSETTS;
        areaCodeArray[858] = State.CALIFORNIA;
        areaCodeArray[859] = State.KENTUCKY;
        areaCodeArray[860] = State.CONNECTICUT;
        areaCodeArray[862] = State.NEW_JERSEY;
        areaCodeArray[863] = State.FLORIDA;
        areaCodeArray[864] = State.SOUTH_CAROLINA;
        areaCodeArray[865] = State.TENNESSEE;
        areaCodeArray[870] = State.ARKANSAS;
        areaCodeArray[872] = State.ILLINOIS;
        areaCodeArray[878] = State.PENNSYLVANIA;
        areaCodeArray[901] = State.TENNESSEE;
        areaCodeArray[903] = State.TEXAS;
        areaCodeArray[904] = State.FLORIDA;
        areaCodeArray[906] = State.MICHIGAN;
        areaCodeArray[907] = State.ALASKA;
        areaCodeArray[908] = State.NEW_JERSEY;
        areaCodeArray[909] = State.CALIFORNIA;
        areaCodeArray[910] = State.NORTH_CAROLINA;
        areaCodeArray[912] = State.GEORGIA;
        areaCodeArray[913] = State.KANSAS;
        areaCodeArray[914] = State.NEW_YORK;
        areaCodeArray[915] = State.TEXAS;
        areaCodeArray[916] = State.CALIFORNIA;
        areaCodeArray[917] = State.NEW_YORK;
        areaCodeArray[918] = State.OKLAHOMA;
        areaCodeArray[919] = State.NORTH_CAROLINA;
        areaCodeArray[920] = State.WISCONSIN;
        areaCodeArray[925] = State.CALIFORNIA;
        areaCodeArray[928] = State.ARIZONA;
        areaCodeArray[929] = State.NEW_YORK;
        areaCodeArray[931] = State.TENNESSEE;
        areaCodeArray[936] = State.TEXAS;
        areaCodeArray[937] = State.OHIO;
        areaCodeArray[938] = State.ALABAMA;
        areaCodeArray[939] = State.PUERTO_RICO;
        areaCodeArray[940] = State.TEXAS;
        areaCodeArray[941] = State.FLORIDA;
        areaCodeArray[947] = State.MICHIGAN;
        areaCodeArray[949] = State.CALIFORNIA;
        areaCodeArray[951] = State.CALIFORNIA;
        areaCodeArray[952] = State.MINNESOTA;
        areaCodeArray[954] = State.FLORIDA;
        areaCodeArray[956] = State.TEXAS;
        areaCodeArray[970] = State.COLORADO;
        areaCodeArray[971] = State.OREGON;
        areaCodeArray[972] = State.TEXAS;
        areaCodeArray[973] = State.NEW_JERSEY;
        areaCodeArray[978] = State.MASSACHUSETTS;
        areaCodeArray[979] = State.TEXAS;
        areaCodeArray[980] = State.NORTH_CAROLINA;
        areaCodeArray[985] = State.LOUISIANA;
        areaCodeArray[989] = State.MICHIGAN;
    }

    /**
     * Load in the valid US phone number area codes and corresponding region code to a map of each area code to all the
     * region codes contained in that area code
     */
    private static void populateValidAreaAndRegionCodes() {
        Properties properties = Property.getPropertyFile("src/test/resources/phone.properties");
        for (String areaCode : properties.stringPropertyNames()) {
            CopyOnWriteArrayList<String> regionList =
                    new CopyOnWriteArrayList<>(Arrays.asList(properties.getProperty(areaCode).split(",")));
            validAreaAndRegionCodes.put(areaCode, regionList);
        }
    }

    /**
     * Return a boolean if an area code exists or not
     *
     * @param areaCode the area code to validate
     * @return boolean - whether or not the area code is valid
     */
    static boolean isValidAreaCode(int areaCode) {
        return areaCodeArray[areaCode] != null;
    }

    /**
     * Generate the area code and region code, using the map loaded by the phone.properties file
     *
     * @return String - the area and region code concatenated
     */
    private static String getRandomValidUsAreaAndRegionCode() {
        if (validAreaAndRegionCodes.size() == 0) {
            populateValidAreaAndRegionCodes();
        }
        int areaCodeIndex = rand.nextInt(validAreaAndRegionCodes.size() - 1);
        List<String> keysAsArray = new ArrayList<>(validAreaAndRegionCodes.keySet());
        String areaCode = keysAsArray.get(areaCodeIndex);
        List<String> regionCodes = validAreaAndRegionCodes.get(areaCode);
        int regionCodeIndex = rand.nextInt(regionCodes.size());
        String regionCode = regionCodes.get(regionCodeIndex);
        return String.format("%s%s", areaCode, regionCode);
    }

    /**
     * Returns a valid randomly generated phone number with valid area and respective region code
     *
     * @return String - the full valid phone number
     */
    public static String generateRandomValidUsPhoneNumber() {
        int phoneNum = rand.nextInt(10000);
        StringBuilder lastFour = new StringBuilder();
        int currLength = String.valueOf(phoneNum).length();
        while (currLength < 4) {
            lastFour.append("0");
            currLength += 1;
        }
        lastFour.append(phoneNum);
        return getRandomValidUsAreaAndRegionCode() + lastFour;
    }

    /**
     * Return the corresponding state for which an area code resides in
     *
     * @param areaCode the area code to validate
     * @return String - the US state for which the area code resides in
     */
    String getUSState(int areaCode) {
        return areaCodeArray[areaCode].getValue();
    }
}
