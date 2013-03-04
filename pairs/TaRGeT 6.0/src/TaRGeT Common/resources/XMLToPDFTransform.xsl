<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:patern="user-view.target.v20071129" xmlns:java="java"  exclude-result-prefixes="fo">	
     <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

     
<xsl:template match="/patern:phone">
     <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
           <fo:region-body/>
           </fo:simple-page-master>
           </fo:layout-master-set>
              <fo:page-sequence master-reference="simpleA4">
                <fo:flow flow-name="xsl-region-body">
					<xsl:for-each select="patern:feature">
						<fo:block font-size="16pt" font-weight="bold" space-after="5mm" line-height="1.5">
							Feature <xsl:value-of select="patern:featureId"/> - <xsl:value-of select="patern:name"/>
						</fo:block>
						<xsl:for-each select="patern:useCase">	
							<fo:block font-size="14pt" font-weight="bold" space-after="3mm">
								<xsl:value-of select="patern:id"/> - <xsl:value-of select="patern:name"/>
							</fo:block>
							<fo:block font-size="11pt" space-after="2mm">Description: <xsl:value-of select="patern:description"/></fo:block>
							<fo:block font-size="11pt"  space-after="5mm">Setup: <xsl:value-of select="patern:setup"/></fo:block>
							<fo:block font-size="11pt" font-weight="bold" space-after="3mm">
								FLOWS 
							</fo:block>
							<xsl:for-each select="patern:flow">	
								<fo:block font-size="10pt" space-after="1mm">Description: <xsl:value-of select="patern:description"/></fo:block>
								<fo:block font-size="10pt" space-after="1mm">From Step: <xsl:value-of select="patern:fromSteps"/></fo:block>
								<fo:block font-size="10pt" space-after="5mm">To Step: <xsl:value-of select="patern:toSteps"/></fo:block>
								
									<fo:block font-size="10pt" space-after="10mm">
										<fo:table table-layout="fixed">
											<fo:table-column column-width="2cm"/>
											<fo:table-column column-width="4cm"/>
											<fo:table-column column-width="5cm"/>
											<fo:table-column column-width="5cm"/>
											<fo:table-header>
												<fo:table-row font-weight="bold">
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:text>Step Id</xsl:text>
														</fo:block>
													</fo:table-cell >
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:text>User Action</xsl:text>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:text>System Condition</xsl:text>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:text>System Response</xsl:text>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="patern:step">
												<fo:table-row>
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:value-of select="patern:stepId"/>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:value-of select="patern:action"/>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:value-of select="patern:condition"/>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell border="1pt solid black" text-align="center">
														<fo:block>
															<xsl:value-of select="patern:response"/>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</fo:block>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>
