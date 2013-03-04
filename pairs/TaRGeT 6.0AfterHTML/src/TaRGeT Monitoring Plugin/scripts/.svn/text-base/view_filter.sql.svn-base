alter view dbo.view_filters
as 

select
distinct f.id 
,pu.id as idProjectUser
,f.type as valueType
,useCase
,req
,path			
from dbo.ProjectUser pu
join dbo.Filter f on f.idProjectuser = pu.id
where path not like '%None%'