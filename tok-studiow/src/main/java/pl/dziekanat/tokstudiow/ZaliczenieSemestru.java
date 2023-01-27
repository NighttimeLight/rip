package pl.dziekanat.tokstudiow;

import java.util.Iterator;
import java.util.Map;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.StringValue;

@ProcessApplication("Zaliczenie Semestru App")
public class ZaliczenieSemestru extends ServletProcessApplication {
	
	@PostDeploy
	public void evaluateDecisionTable(ProcessEngine processEngine) {
		DecisionService decisionService = processEngine.getDecisionService();
		VariableMap variables =  Variables.createVariables()
				.putValue("podanie_punktyECTS", 16)
				.putValue("podanie_uzasadnienie", "HERE_uzasadnienie______________________________________123");
		
		DmnDecisionTableResult decisionResult = decisionService.evaluateDecisionTableByKey("OcenaPodania", variables);
		DmnDecisionRuleResult sr = decisionResult.getSingleResult();
		if (decisionResult.getSingleResult().containsKey("decyzja_czyZaliczono")) {
			Boolean zal = decisionResult.getSingleResult().getEntry("decyzja_czyZaliczono");
			System.out.println("Czy zaliczono: " + zal);
		}
		StringValue uzasadnienie = sr.getEntryTyped("decyzja_uzasadnienie");
		System.out.println("Uzasadnienie: " + uzasadnienie);
		
		for (Map<String, Object> result : decisionResult.getResultList()) {
			for (Map.Entry<String, Object> entry : result.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
		}
		
	}

}
