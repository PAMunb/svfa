/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Basic22.java,v 1.5 2006/04/04 20:00:40 livshits Exp $
 */
package samples.basic;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/** 
 *  @servlet description="basic path traversal" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic22  {
    private static final String FIELD_NAME = "name";

    private String source() {
        return "secret";
    }

    protected void main() throws IOException {
        String s = source();
        String name = "abc" + s;
        File f = new File(name);
        f.createNewFile();
    }
}