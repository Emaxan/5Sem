package test;

import my.library.DataAccess;
import my.library.ServiceClass;
import my.library.ServiceClassImpl;
import my.library.ServiceException;
import org.jmock.integration.junit4.JMock;
import org.junit.runner.RunWith;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

@RunWith(JMock.class)
public class ServiceClassTest {
    private final int ID = 5;
    private final int WRONG_ID = 6;
    private ServiceClass systemUnderTest;
    private DataAccess mockedDependency;
    private Mockery mockingContext = new JUnit4Mockery();

    @Before
    public void doBeforeEachTestCase() {
        mockingContext = new JUnit4Mockery();
        systemUnderTest = new ServiceClassImpl();
        mockedDependency = mockingContext.mock(DataAccess.class);
        systemUnderTest.setDataAccess(mockedDependency);
    }

    @Test
    public void getEmail() throws ServiceException {
        mockingContext.checking(new Expectations() {
            {
                oneOf(mockedDependency).getEmail(ID);
                will(returnValue("myCoolEmail"));
            }
        });
        String testEmail = systemUnderTest.getEmail(ID);
    }

    @Test(expected = ServiceException.class)
    public void getEmailNonExistentIdThrowsException() throws ServiceException {
        mockingContext.checking(new Expectations() {
            {
                oneOf(mockedDependency).getEmail(WRONG_ID);
                will(returnValue(null));
            }
        });
        final String testEmail = systemUnderTest.getEmail(WRONG_ID);
    }

    @Test
    public void getEmailFiveTimes() throws ServiceException {
        mockingContext.checking(new Expectations() {
            {
                exactly(5).of(mockedDependency).getEmail(ID);
                will(returnValue("myCoolEmail"));
            }
        });
        String testEmail;
        for (int i = 0; i<5;i++)
            testEmail = systemUnderTest.getEmail(ID);
    }

    @Test
    public void getEmailZeroTimes() throws ServiceException {
        mockingContext.checking(new Expectations() {
            {

                never(mockedDependency).getEmail(ID);
                will(returnValue("myCoolEmail"));
            }
        });
        String testEmail;
        for (int i = 0; i<0;i++)
            testEmail = systemUnderTest.getEmail(ID);
    }

    @Test
    public void getEmailAtLeastFiveTimes() throws ServiceException {
        mockingContext.checking(new Expectations() {
            {
                atLeast(5).of(mockedDependency).getEmail(ID);
                will(returnValue("myCoolEmail"));
            }
        });
        String testEmail;
        for (int i = 0; i<6;i++)
            testEmail = systemUnderTest.getEmail(ID);
    }

    @Test
    public void getEmailAtMostFiveTimes() throws ServiceException {
        mockingContext.checking(new Expectations() {
            {
                atMost(5).of(mockedDependency).getEmail(ID);
                will(returnValue("myCoolEmail"));
            }
        });
        String testEmail;
        for (int i = 0; i<5;i++)
            testEmail = systemUnderTest.getEmail(ID);
    }
}
