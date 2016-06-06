package cz.meza.test.app.bean.impl;

import javax.enterprise.context.ApplicationScoped;

/**
 * Test bean for counting.
 * @author vladimir.mezera@gmail.com
 */
@ApplicationScoped
public class ApplicationCounter {

    private Integer counter = 0;


    public Integer getActualCounter() {
        return counter++;
    }

}
