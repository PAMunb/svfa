/**
    @author Test case for String.concat() rule
    
    $Id: Basic22Concat.java,v 1.0 2024/12/15 Test $
 */
package samples.basic;

import java.io.File;
import java.io.IOException;

/** 
 *  @servlet description="basic path traversal with concat" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic22Concat  {

    private String source() {
        return "secret";
    }

    protected void main() throws IOException {
        String s = source();
        String name = s.concat("abc");  // Using concat instead of +
        File f = new File(name);
        f.createNewFile();
    }
}

