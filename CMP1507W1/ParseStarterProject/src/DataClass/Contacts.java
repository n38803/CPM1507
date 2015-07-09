package DataClass;
/**
 * Shaun Thompson - CMP1507
 */

import java.io.Serializable;

public class Contacts {

    private String mName;
    private String mNumber;

    public Contacts (String _name, String _number) {
        mName = _name;
        mNumber = _number;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

}