package cz.muni.fi.pa165.secretagency;

/**
 * Facade for data initialization.
 *
 * @author Jan Pavlu (487548)
 */
public interface InitDataFacade {

    /**
     * Creates init data and loads it into in memory database.
     */
    void loadInitData();
}
