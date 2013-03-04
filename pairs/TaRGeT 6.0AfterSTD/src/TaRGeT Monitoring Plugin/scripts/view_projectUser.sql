create view view_projectUser 
as 

select 
pu.id
,p.id as idProject
,tu.id as idUser
,tu.userName
,pu.openDate
from dbo.Project p
join dbo.ProjectUser pu on pu.idProject = p.id
join dbo.TargetUser tu on tu.id = pu.idUser