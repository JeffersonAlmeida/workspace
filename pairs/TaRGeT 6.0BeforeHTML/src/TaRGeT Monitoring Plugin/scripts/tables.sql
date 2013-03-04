drop table Exception
drop table Filter
drop table TestUseCase
drop table UseCase
drop table TestCase
drop table ProjectUser
drop table Project
drop table TargetUser

create table dbo.TargetUser(
		id int identity(1,1)
	 ,userName varchar(30)	 
	 ,CONSTRAINT PK_TargetUser_Id PRIMARY KEY(id)
)


create table Project (
		 id int identity(1,1)
		,projectName varchar(50)
		,localFolder varchar(50)
		,creationDate smalldatetime
		,userName varchar(500)
		,CONSTRAINT PK_Project_Id PRIMARY KEY(id)
)




create table ProjectUser(
		 id int identity(1,1)
		,idProject int
		,idUser int
		,openDate smalldatetime
		,CONSTRAINT PK_ProjectUser_Id PRIMARY KEY(id)
		,CONSTRAINT FK_ProjectUser_idProject FOREIGN KEY(idProject) REFERENCES dbo.Project(id)
		,CONSTRAINT FK_ProjectUser_IdUser FOREIGN KEY(idUser) REFERENCES dbo.TargetUser(id)
)

create table dbo.TestCase (
	 id int identity(1,1)
	,idTargetUser int
	,idProjectUser int
	,name varchar(40)
	,date smalldatetime	
	,CONSTRAINT PK_TestCase_Id PRIMARY KEY(id)
	,CONSTRAINT FK_TestCase_IdTargetUser FOREIGN KEY(idTargetUser) REFERENCES dbo.TargetUser(id)
	,CONSTRAINT FK_TestCase_idProjectUser FOREIGN KEY(idProjectUser) REFERENCES dbo.ProjectUser(id)
)


create table dbo.UseCase(
		 id int identity(1,1)
		,useCaseName varchar(20)		
		,CONSTRAINT PK_UseCase_Id PRIMARY KEY(id)
)



create table dbo.TestUseCase(
		 id int identity(1,1)
		,idUseCase int
		,idTestCase int		
		,CONSTRAINT PK_TestUseCase_Id PRIMARY KEY(id)
		,CONSTRAINT FK_TestUseCase_IdUseCase FOREIGN KEY(idUseCase) REFERENCES dbo.UseCase(id)
		,CONSTRAINT FK_TestUseCase_IdTestCase FOREIGN KEY(idTestCase) REFERENCES dbo.TestCase(id)
)




create table Exception(
		 id int identity(1,1)
		,name varchar(50)
		,idProjectUser int
		,throwDate smalldatetime
		,CONSTRAINT PK_Exception_Id PRIMARY KEY (id)
		,CONSTRAINT FK_Exception_idProjectUser FOREIGN KEY(idProjectUser) REFERENCES dbo.ProjectUser(id)
)


create table Filter(
		 id int identity(1,1)
		,idProjectUser int
		,req varchar(500)
		,useCase varchar(500)
		,path varchar(10)
		,type varchar(2)
		,CONSTRAINT PK_UseCaseFilter_Id PRIMARY KEY(Id)
		,CONSTRAINT FK_UseCaseFilter_idProjectUser FOREIGN KEY(idProjectUser) REFERENCES dbo.ProjectUser(id)
		
)


