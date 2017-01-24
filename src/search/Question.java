package search;

import javax.management.Query;
import javax.rmi.CORBA.Util;

public abstract class Question
{
    static String table;
    static String idField = "id";

    public static boolean exists(long id) throws Exception
    {
        Db db = Util.getDb();
        Query q = db.query();
        q.select( idField ).whereLong(idField, id).limit(1).get(table);

        return q.hasResults();
    }

    //snip..
}

public class B extends Question {
    static String str = " string in b";
}
