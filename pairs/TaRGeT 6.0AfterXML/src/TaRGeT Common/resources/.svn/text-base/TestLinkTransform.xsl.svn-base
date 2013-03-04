<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="ISO-8859-1"
		indent="yes" cdata-section-elements="details summary steps expectedresults" />
	<xsl:template match="/">
		<testsuite>
			<xsl:attribute name="name">
    			<xsl:value-of select="testSuite/@name" />
  			</xsl:attribute>
			<details><![CDATA[<p><h1>Requirements Traceability Matrix - Requirements</h1><table border="1"><tr><th>Requirement</th><th>Use Case</th></tr>]]><xsl:for-each select="testSuite/rtmRequirements/rtmRequirement"><![CDATA[<tr><td>]]><xsl:value-of select="requirement"/><![CDATA[</td><td>]]><xsl:value-of select="useCase"/><![CDATA[</td></tr>]]></xsl:for-each><![CDATA[</table></p>]]><![CDATA[<p><h1>Requirements Traceability Matrix - System Tests</h1><table border="1"><tr><th>Requirement</th><th>System Test Case</th></tr>]]><xsl:for-each select="testSuite/rtmSystemTests/rtmSystemTest"><![CDATA[<tr><td>]]><xsl:value-of select="requirement"/><![CDATA[</td><td>]]><xsl:value-of select="systemTestCase"/><![CDATA[</td></tr>]]></xsl:for-each><![CDATA[</table></p>]]><![CDATA[<p><h1>Requirements Traceability Matrix - Use Case</h1><table border="1"><tr><th>Use Case</th><th>System Test Case</th></tr>]]><xsl:for-each select="testSuite/rtmUseCases/rtmUseCase"><![CDATA[<tr><td>]]><xsl:value-of select="useCase"/><![CDATA[</td><td>]]><xsl:value-of select="systemTestCase"/><![CDATA[</td></tr>]]></xsl:for-each><![CDATA[</table></p>]]></details>
			<xsl:for-each select="testSuite/testCases/testCase">
				<testcase>
					<xsl:attribute name="name">
    					<xsl:value-of select="concat(substring(concat(tcIdHeader,': ',description), 1, 97), '...')" />
  					</xsl:attribute>
					<summary><![CDATA[<p><strong>Test Case ID: </strong><span style='display:none'>#testCase#</span>]]> <xsl:value-of select="tcIdHeader" /> <![CDATA[<br /><strong>Regression Level: </strong><span style='display:none'>#regressionLevel#</span>]]> <xsl:value-of select="regressionLevel" /> <![CDATA[<br /><strong>Execution Type: </strong><span style='display:none'>#executionType#</span>]]> <xsl:value-of select="executionType" /> <![CDATA[<br /><strong>Descritpion: </strong><span style='display:none'>#description#</span>]]> <xsl:value-of select="description" /> <![CDATA[<br /><strong>Objective: </strong><span style='display:none'>#objective#</span>]]> <xsl:value-of select="objective" /> <![CDATA[<br /><span style='display:none'>#id#]]> <xsl:value-of select="id" /> <![CDATA[</span><br /></p><p><strong>Use Case References: </strong><span style='display:none'>#useCaseReferences#</span>]]> <xsl:value-of select="useCaseReferences" /> <![CDATA[<br /><strong>Requirements: </strong><span style='display:none'>#requirements#</span>]]> <xsl:value-of select="requirements" /> <![CDATA[<br /><strong>Setups: </strong><span style='display:none'>#setups#</span>]]> <xsl:value-of select="setups" /> <![CDATA[<br /><strong>Initial Conditions: </strong><span style='display:none'>#initialConditions#</span>]]> <xsl:value-of select="initialConditions" /> <![CDATA[<br /><strong>Note: </strong><span style='display:none'>#note#</span>]]> <xsl:value-of select="note" /> <![CDATA[<br /><strong>Final Conditions: </strong><span style='display:none'>#finalConditions#</span>]]> <xsl:value-of select="finalConditions" /> <![CDATA[<br /><strong>Cleanup: </strong><span style='display:none'>#cleanup#</span>]]> <xsl:value-of select="cleanup" /> <![CDATA[<br /></p>]]></summary>
					<steps><xsl:for-each select="steps/step"><![CDATA[<p><span style='display:none'>#stepId#</span>]]> <xsl:value-of select="position()"/> <![CDATA[<span style='display:none'>#action#</span>]]> <xsl:value-of select="action" /> <![CDATA[<br /></p>]]></xsl:for-each></steps>
					<expectedresults><xsl:for-each select="steps/step"><![CDATA[<p>]]><xsl:value-of select="position()"/> <![CDATA[<span style='display:none'>#response#</span>]]> <xsl:value-of select="response" /> <![CDATA[<br /></p>]]></xsl:for-each></expectedresults>
					<requirements>
						<xsl:call-template name="output-tokens">
							<xsl:with-param name="list"><xsl:value-of select="concat(requirements, ',')" /></xsl:with-param>
						</xsl:call-template>						
					</requirements>
				</testcase>
			</xsl:for-each>
		</testsuite>
	</xsl:template>
	
	<xsl:template name="output-tokens">
      <xsl:param name="list" />
      <xsl:variable name="first" select="substring-before($list, ',')" />
      <xsl:variable name="remaining" select="substring-after($list, ',')" />
	  <requirement>
		<req_spec_title><![CDATA[TaRGeT_Requirements]]></req_spec_title>
		<doc_id><![CDATA[]]><xsl:value-of select="$first" /><![CDATA[]]></doc_id>
		<title><![CDATA[]]></title>
	  </requirement>
      <xsl:if test="$remaining">
          <xsl:call-template name="output-tokens">
              <xsl:with-param name="list" select="$remaining" />
          </xsl:call-template>
      </xsl:if>
  </xsl:template>
  
</xsl:stylesheet>
