CREATE view dbo.view_target
as 
select
tc.id as idTestCase
 ,tc.name as testCaseName
,tc.date
,uc.useCaseName
,tu.userName
,(select COUNT(id) from TestUseCase where idTestCase = tuc.idTestCase) as useCaseCount
from dbo.TestCase tc
join dbo.TestUseCase tuc on tuc.idTestCase = tc.id
join dbo.UseCase uc on uc.id = tuc.idUseCase
join dbo.TargetUser tu on tu.id = tc.idTargetUser