package com.fugru.util;

import ezvcard.Ezvcard;
import ezvcard.VCard;

/**
 * vCard utils.
 *
 * @author vkolodrevskiy
 */
public class VCardUtils {

    public static String vcardToJson(String vCardString, boolean prettyPrint) {
        VCard vCard = Ezvcard.parse(vCardString).first();
        return Ezvcard.writeJson(vCard).prettyPrint(prettyPrint).go();
    }
}
