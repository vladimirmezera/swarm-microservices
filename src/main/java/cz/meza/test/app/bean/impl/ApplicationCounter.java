package cz.meza.test.app.bean.impl;

import cz.meza.test.app.bean.ApplicationCounterService;

import javax.enterprise.context.ApplicationScoped;

/**
 * Test bean for counting.
 * @author vladimir.mezera@gmail.com
 */
@ApplicationScoped
public class ApplicationCounter implements ApplicationCounterService {

    private Integer counter = 0;

    @Override
    public Integer getActualCounter() {
        return counter++;
    }

}
