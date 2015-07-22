package medical.nr.com.medical;

import com.robotium.solo.Solo;
import com.roller.medicine.ui.LoginActivity;

/**
 * @author Hua_
 * @Description:
 * @date 2015/7/14 - 10:30
 */
public class MainActivityTest extends android.test.ActivityInstrumentationTestCase2<LoginActivity>{
    private Solo solo;
    public MainActivityTest(Class activityClass) {
        super(activityClass);
    }

    @SuppressWarnings("deprecation")
    public MainActivityTest() {  //初始化函数 告诉系统要测试的APP是什么。
        super("com.roller.medicine",LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    //  测试具体实例
    public void testUI()throws Exception
    {
        boolean expected =true;

        boolean actual =solo.searchText("Hello")&& solo.searchText("world!");
        assertEquals("This and/or is are not found",expected,actual);
    }
}

