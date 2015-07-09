package DataClass;
/**
 * Shaun Thompson - CMP1507
 */

import java.io.Serializable;

public class Contacts implements Serializable {

    private static final long serialVersionUID = 517116325584636891L;

    private String mName;
    private int mNumber;

    public Contacts (String _name, int _number) {
        mName = _name;
        mNumber = _number;
    }

    public String getName() {
        return mName;
    }

    public int getNumber() {
        return mNumber;
    }

}