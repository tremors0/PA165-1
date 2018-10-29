package cz.fi.muni.pa165.secretagency;

import cz.fi.muni.pa165.secretagency.dao.MissionDao;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;

/**
 * Tests for Mission DAO implementation. These test don't cover edge cases (exceptions)
 * because those will be handled by Service layer.
 *
 * @author Jan Pavlu (487548)
 */
@ContextConfiguration(classes = SecretAgencyPersistenceApplicationContext.class)
public class MissionDaoTests extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private MissionDao missionDao;

    private Mission vietnam;
    private Mission afghanistan;

    @BeforeClass
    public void createMissions() {
        vietnam = new Mission();
        vietnam.setLatitude(14.315424);
        vietnam.setLongitude(108.339537);
        vietnam.setStarted(LocalDate.of(1955, 1, 1));
        vietnam.setEnded(LocalDate.of(1975, 4, 30));
        vietnam.setMissionType(MissionTypeEnum.SABOTAGE);

        afghanistan = new Mission();
        afghanistan.setLatitude(34.543896);
        afghanistan.setLongitude(69.160652);
        afghanistan.setStarted(LocalDate.of(2001, 5, 5));
        afghanistan.setMissionType(MissionTypeEnum.ASSASSINATION);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(vietnam);
        em.persist(afghanistan);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Tests if missions with selected type are returned.
     */
    @Test
    public void getMissionWithType() {
        List<Mission> sabotageMissions = missionDao.getMissionsWithType(MissionTypeEnum.SABOTAGE);
        Assert.assertEquals(sabotageMissions.size(),1);
        Mission selectedSabotageMission = sabotageMissions.get(0);
        Assert.assertEquals(selectedSabotageMission, vietnam);

        List<Mission> assassinationMissions = missionDao.getMissionsWithType(MissionTypeEnum.ASSASSINATION);
        Assert.assertEquals(assassinationMissions.size(), 1);
        Mission selectedAssassinationMission = assassinationMissions.get(0);
        Assert.assertEquals(selectedAssassinationMission, afghanistan);
    }

    /**
     * Tests if no mission is returned, when no mission with selected type exists.
     */
    @Test
    public void getMissionWithTypeNoMission() {
        List<Mission> espionageMission = missionDao.getMissionsWithType(MissionTypeEnum.ESPIONAGE);
        Assert.assertEquals(espionageMission.size(), 0);
    }

    /**
     * Tests if missions from selected interval are returned.
     */
    @Test
    public void getMissionsStartedInInterval() {
        LocalDate dateFrom = LocalDate.of(1990, 1, 1);
        LocalDate dateTo = LocalDate.of(2011, 12, 31);
        List<Mission> missionsInInterval = missionDao.getMissionsStartedInInterval(dateFrom, dateTo);
        Assert.assertEquals(missionsInInterval.size(),1);
        Mission selectedMission = missionsInInterval.get(0);
        Assert.assertEquals(selectedMission, afghanistan);
    }

    /**
     * Tests if correct mission is returned, when date from is the last day of the mission
     *   and date to is one day before a next mission.
     */
    @Test
    public void getMissionsStartedInIntervalBorderValues() {
        LocalDate dateFrom = LocalDate.of(1975, 4, 30);
        LocalDate dateTo = LocalDate.of(2001, 5, 4);
        List<Mission> missionsInInterval = missionDao.getMissionsStartedInInterval(dateFrom, dateTo);
        Assert.assertEquals(missionsInInterval.size(), 1);
        Mission selectedMission = missionsInInterval.get(0);
        Assert.assertEquals(selectedMission, vietnam);
    }

    /**
     * Tests if no mission is returned when interval is set exactly between 2 missions.
     */
    @Test
    public void getMissionsStartedInIntervalNoMission() {
        LocalDate dateFrom = LocalDate.of(1975, 5, 1);
        LocalDate dateTo = LocalDate.of(2001, 5, 4);
        List<Mission> missionsInInterval = missionDao.getMissionsStartedInInterval(dateFrom, dateTo);
        Assert.assertEquals(missionsInInterval.size(), 0);
    }

    /**
     * Tests if correct mission is returned, if there is one active mission.
     */
    @Test
    public void getActiveMissions() {
        List<Mission> activeMissions = missionDao.getActiveMissions();
        Assert.assertEquals(activeMissions.size(), 1);
        Mission activeMission = activeMissions.get(0);
        Assert.assertEquals(activeMission, afghanistan);
    }

    /**
     * Checks if no mission is returned, if all mission ended.
     */
    @Test
    public void getActiveMissionsNoMission() {
        // change date ended so Afghanistan is not active anymore
        LocalDate originalDateEnded = afghanistan.getEnded();
        afghanistan.setEnded(LocalDate.of(2011, 9, 11));
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        afghanistan = em.merge(afghanistan);
        em.getTransaction().commit();
        em.close();

        List<Mission> activeMissions = missionDao.getActiveMissions();
        Assert.assertEquals(activeMissions.size(), 0);

        // restore original date
        afghanistan.setEnded(originalDateEnded);
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        afghanistan = em2.merge(afghanistan);
        em2.getTransaction().commit();
        em2.close();
    }

    /**
     * Tests if correct missions are returned, when there is more active missions.
     */
    @Test
    public void getActiveMissionsMoreMissions() {
        // change date ended for Vietnam to null - mission is active
        LocalDate originalDateEnded = vietnam.getEnded();
        vietnam.setEnded(null);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        vietnam = em.merge(vietnam);
        em.getTransaction().commit();
        em.close();

        List<Mission> activeMissions = missionDao.getActiveMissions();
        Assert.assertEquals(activeMissions.size(), 2);
        Assert.assertTrue(activeMissions.contains(vietnam));
        Assert.assertTrue(activeMissions.contains(afghanistan));

        // restore original date
        vietnam.setEnded(originalDateEnded);
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        vietnam = em2.merge(vietnam);
        em2.getTransaction().commit();
        em2.close();
    }

    /**
     * Tests if correct mission is returned, in case that mission has started today.
     */
    @Test
    public void getActiveMissionStartedToday() {
        // change date from for afghanistan to today
        LocalDate originalDateStarted = afghanistan.getStarted();
        afghanistan.setStarted(LocalDate.now());
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        afghanistan = em.merge(afghanistan);
        em.getTransaction().commit();
        em.close();

        List<Mission> activeMissions = missionDao.getActiveMissions();
        Assert.assertEquals(activeMissions.size(), 1);
        Assert.assertTrue(activeMissions.contains(afghanistan));

        // restore original date
        afghanistan.setStarted(originalDateStarted);
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        afghanistan = em2.merge(afghanistan);
        em2.getTransaction().commit();
        em2.close();
    }

    /**
     * Tests if mission which takes place in selected location is returned.
     */
    @Test
    public void getMissionsInPlace() {
        List<Mission> missionsInPlace = missionDao.getMissionsInPlace(vietnam.getLatitude(), vietnam.getLongitude());
        Assert.assertEquals(missionsInPlace.size(), 1);
        Assert.assertTrue(missionsInPlace.contains(vietnam));
    }

    /**
     * Tests if no mission is returned, when the location is different from every mission.
     */
    @Test
    public void getMissionsInPlaceNoMissions() {
        List<Mission> missionsInPlace = missionDao.getMissionsInPlace(123.5, 55.0);
        Assert.assertEquals(missionsInPlace.size(), 0);
    }
}
