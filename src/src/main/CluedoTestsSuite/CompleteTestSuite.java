package src.main.CluedoTestsSuite;


	import org.junit.runner.RunWith;
	import org.junit.runners.Suite;

import src.main.CluedoTests.AccusingAndSuggestingTests;
import src.main.CluedoTests.CardTests;
import src.main.CluedoTests.PlayerTests;
import src.main.CluedoTests.WeaponTests;

	@RunWith(Suite.class)
	@Suite.SuiteClasses({
	        src.main.CluedoTests.AccusingAndSuggestingTests.class,
	        src.main.CluedoTests.CardTests.class,
	        src.main.CluedoTests.PlayerTests.class,
	        src.main.CluedoTests.WeaponTests.class
	})
	public class CompleteTestSuite {
	}


