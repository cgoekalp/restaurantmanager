package at.ac.tuwien.sepm.assignment.individual.universe.service;

import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.JDBCProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.dao.DB.IProductDAO;
import at.ac.tuwien.sepm.assignment.individual.universe.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SimpleUniverseService implements UniverseService{

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int SLEEP_SECONDS = 2;

    @Override
    public String calculateAnswer() {
        return null;
    }

}
