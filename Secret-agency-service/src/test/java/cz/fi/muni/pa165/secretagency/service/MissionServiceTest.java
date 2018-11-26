package cz.fi.muni.pa165.secretagency.service;

import cz.fi.muni.pa165.secretagency.dao.MissionDao;
import cz.fi.muni.pa165.secretagency.entity.Mission;
import cz.fi.muni.pa165.secretagency.enums.MissionTypeEnum;
import cz.fi.muni.pa165.secretagency.service.config.ServiceConfiguration;
import cz.fi.muni.pa165.secretagency.service.exceptions.MissionServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Adam Skurla (487588)
 *
 * Tests for Mission Service
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MissionServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    MissionDao missionDao;

    @Autowired
    private MissionService missionService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(missionService, "dao", missionDao);
    }

    @Test
    public void getMissionsWithTypeOk() {
        Mission mission1 = new Mission();
        mission1.setLatitude(14.315424);
        mission1.setLongitude(108.339537);
        mission1.setStarted(LocalDate.of(1955, 1, 1));
        mission1.setEnded(LocalDate.of(1975, 4, 30));
        mission1.setMissionType(MissionTypeEnum.SABOTAGE);

        when(missionDao.getMissionsWithType(MissionTypeEnum.SABOTAGE)).thenReturn(Collections.singletonList(mission1));
        Assert.assertEquals(missionService.getMissionsWithType(MissionTypeEnum.SABOTAGE), Collections.singletonList(mission1));
    }


    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Mission type is null")
    public void getMissionsWithTypeNOk() {
        missionService.getMissionsWithType(null);
    }

    @Test
    public void getMissionsStartedInIntervalOk() {
        Mission mission1 = new Mission();
        mission1.setLatitude(14.315424);
        mission1.setLongitude(108.339537);
        mission1.setStarted(LocalDate.of(1955, 1, 1));
        mission1.setEnded(LocalDate.of(1975, 4, 30));
        mission1.setMissionType(MissionTypeEnum.SABOTAGE);

        Mission mission2 = new Mission();
        mission2.setLatitude(17.34243);
        mission2.setLongitude(112.65421);
        mission2.setStarted(LocalDate.of(1970, 5, 2));
        mission2.setEnded(LocalDate.of(1982, 5, 14));
        mission2.setMissionType(MissionTypeEnum.ESPIONAGE);

        LocalDate startDate = LocalDate.of(1969, 1, 2);
        LocalDate endDate = LocalDate.of(1971, 2, 2);

        List<Mission> missionList = Arrays.asList(mission1, mission2);

        when(missionDao.getMissionsStartedInInterval(startDate, endDate)).thenReturn(missionList);

        Assert.assertEquals(missionService.getMissionsStartedInInterval(startDate, endDate), missionList);
    }

    @Test(expectedExceptions = NullPointerException.class,
            expectedExceptionsMessageRegExp = "Mission start date or end date is null")
    public void getMissionsStartedInIntervalParamsNull() {
        missionService.getMissionsStartedInInterval(null, null);
    }

    @Test(expectedExceptions = MissionServiceException.class,
            expectedExceptionsMessageRegExp = "End date is before start date")
    public void getMissionsStartedInIntervalEndBeforeStart() {
        LocalDate startDate = LocalDate.of(1971, 2, 2);
        LocalDate endDate = LocalDate.of(1969, 1, 2);

        missionService.getMissionsStartedInInterval(startDate, endDate);
    }

    @Test
    public void getMissionsInPlaceOk() {
        Mission mission1 = new Mission();
        mission1.setLatitude(14.315424);
        mission1.setLongitude(108.339537);
        mission1.setMissionType(MissionTypeEnum.SABOTAGE);

        Mission mission2 = new Mission();
        mission2.setLatitude(14.315424);
        mission2.setLongitude(108.339537);
        mission1.setMissionType(MissionTypeEnum.ASSASSINATION);

        when(missionDao.getMissionsInPlace(14.315424, 108.339537)).thenReturn(Arrays.asList(mission1, mission2));

        Assert.assertEquals(missionService.getMissionsInPlace(14.315424, 108.339537), Arrays.asList(mission1, mission2));
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Latitude or longitude is null")
    public void getMissionsInPlaceParamsNull() {
        missionService.getMissionsInPlace(null, null);
    }

    @Test
    public void getActiveMissionsOk() {
        Mission mission1 = new Mission();
        mission1.setLatitude(14.315424);
        mission1.setLongitude(108.339537);
        mission1.setMissionType(MissionTypeEnum.ASSASSINATION);
        mission1.setStarted(LocalDate.of(2015, 12, 14));

        when(missionDao.getActiveMissions()).thenReturn(Collections.singletonList(mission1));

        Assert.assertEquals(missionService.getActiveMissions(), Collections.singletonList(mission1));
    }
}
