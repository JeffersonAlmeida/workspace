<?xml version="1.0"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;"> ]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
		<title><xsl:value-of select="testSuite/@name"/></title>
	</head>
	<body bgcolor="#A4D3EE">
	
	<h2>Test Cases</h2>
	<xsl:for-each select="testSuite/testCases/testCase">
		
		<div style="border-width:thin;  background-color:#E8E8E8; border-style:solid; padding-left:10px;">
		<span style="display: none;">#testCase#</span>
		<h3>Test Case ID:&nbsp;<xsl:value-of select="tcIdHeader"/></h3>
			<span style="display: none;">ID: <xsl:value-of select="id"/></span>
			<p>
				
				<b>Regression Level:</b>&nbsp;<xsl:value-of select="regressionLevel"/><br/>
				<b>Execution Type:</b>&nbsp;<xsl:value-of select="executionType"/><br/>
				<b>Description:</b>&nbsp;<xsl:value-of select="description"/><br/>
				<b>Objective:</b>&nbsp;<xsl:value-of select="objective"/>
			</p>
			<p>
				<b>Use Case References:</b>&nbsp;<xsl:value-of select="useCaseReferences"/><br/>
				<b>Requirements:</b>&nbsp;<xsl:value-of select="requirements"/><br/>
				<b>Setups:</b>&nbsp;<xsl:value-of select="setups"/>
			</p>
			<p><b>Initial Conditions:</b>&nbsp;<xsl:value-of select="initialConditions"/></p>
			<table border="1">
				<tr>
					<th bgcolor="#A4D3EE">Steps</th>
					<th bgcolor="#A4D3EE">Expected Results</th>
				</tr>
				<xsl:for-each select="steps/step">
				<tr>
					<td><xsl:value-of select="position()"/>)&nbsp;<span style="display: none;">###</span><xsl:value-of select="action"/></td>
					<td><span style="display: none;">###</span><xsl:value-of select="response"/></td>
				</tr>
				</xsl:for-each>				
			</table>
			<p>
				<b>Final Conditions:</b>&nbsp;<xsl:value-of select="finalConditions"/><br/>
				<b>Cleanup:</b>&nbsp;<xsl:value-of select="cleanup"/><br/>
				<b>Notes:</b>&nbsp;<xsl:value-of select="status"/>
			</p>
		</div>
		<br/>
	</xsl:for-each>
	<span style="display: none;">#testCase#</span>
	<h2>Requirements Traceability Matrix - Requirements</h2>
	<div style="border-width:thin;  background-color:#E8E8E8; border-style:solid; padding-left:20px;">
		<br/>
		<table border="1">
				<tr>
					<th bgcolor="#A4D3EE">Requirement</th>
					<th bgcolor="#A4D3EE">Use Case</th>
				</tr>
				<xsl:for-each select="testSuite/rtmRequirements/rtmRequirement">
				<tr>
					<td><xsl:value-of select="requirement"/></td>
					<td><xsl:value-of select="useCase"/></td>
				</tr>
				</xsl:for-each>				
		</table>
		<br/>
	</div>
	<br/>
	<h2>Requirements Traceability Matrix - System Test</h2>
	<div style="border-width:thin;  background-color:#E8E8E8; border-style:solid; padding-left:20px;">
		<br/>
		<table border="1">
				<tr>
					<th bgcolor="#A4D3EE">Requirement</th>
					<th bgcolor="#A4D3EE">System Test Case</th>
				</tr>
				<xsl:for-each select="testSuite/rtmSystemTests/rtmSystemTest">
				<tr>
					<td><xsl:value-of select="requirement"/></td>
					<td><xsl:value-of select="systemTestCase"/></td>
				</tr>
				</xsl:for-each>				
		</table>
		<br/>
	</div>
	<br/>
	<h2>Requirements Traceability Matrix - Use Case</h2>
	<div style="border-width:thin;  background-color:#E8E8E8; border-style:solid; padding-left:20px;">
		<br/>
		<table border="1">
				<tr>
					<th bgcolor="#A4D3EE">Use Case</th>
					<th bgcolor="#A4D3EE">System Test Case</th>
				</tr>
				<xsl:for-each select="testSuite/rtmUseCases/rtmUseCase">
				<tr>
					<td><xsl:value-of select="useCase"/></td>
					<td><xsl:value-of select="systemTestCase"/></td>
				</tr>
				</xsl:for-each>				
		</table>
		<br/>
	</div>
	
	</body>
</html>
</xsl:template>
</xsl:stylesheet>