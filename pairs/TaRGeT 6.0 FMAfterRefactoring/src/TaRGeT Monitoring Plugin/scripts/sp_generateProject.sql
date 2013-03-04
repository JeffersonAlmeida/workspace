exec sp_generateProject @operacao=N'x',@idProject=2
declare @p1 int
set @p1=2
exec sp_prepexec @p1 output,N'@P0 nvarchar(4000),@P1 nvarchar(4000),@P2 nvarchar(4000),@P3 nvarchar(4000)',N'EXEC sp_generateProject @P0, @P1, @P2, @P3                                ',N'C:\Documents and Settings\João Paulo\Desktop\teste1\NewProject',N'NewProject',N'jp',N'i'
select @p1
create procedure sp_generateProject(
 @localFolder varchar(500) = null
,@projectName varchar(50) = null
,@user varchar(50) = null
,@operacao varchar(1) = 'i'
,@idProject int = null
)
as 
	if @operacao = 'x' begin
	  declare @temp table( idTestCase int, idUseCase int) 	

		insert @temp
		select tuc.idTestCase
		,uc.id as idUseCase
		from dbo.UseCase uc
		join dbo.TestUseCase tuc on tuc.idUseCase = uc.id
		join dbo.TestCase tc on tc.id = tuc.idTestCase
		join dbo.ProjectUser pu on pu.id = @idProject
		
		delete from dbo.TestUseCase where idTestCase in(
				select tuc.idTestCase
				from dbo.UseCase uc
				join @temp tuc on tuc.idUseCase = uc.id
				join dbo.TestCase tc on tc.id = tuc.idTestCase
				join dbo.ProjectUser pu on pu.id = tc.idProjectUser
				where pu.id = @idProject
		)
		
		--delete from dbo.UseCase where id in(
		--		select uc.id
		--		from dbo.UseCase uc
		--		join @temp tuc on tuc.idUseCase = uc.id
		--		join dbo.TestCase tc on tc.id = tuc.idTestCase
		--		join dbo.ProjectUser pu on pu.id = tc.idProjectUser
		--		where pu.id = @idProject
		--)
		delete from dbo.TestCase where id in(
						select tc.id
						from @temp tuc
						join dbo.TestCase tc on tc.id = tuc.idTestCase
						join dbo.ProjectUser pu on pu.id = tc.idProjectUser
						where pu.id = @idProject
				)
		
		delete from Filter where id in(
			select f.id
			from Filter f
			join dbo.ProjectUser pu on pu.id = f.idProjectUser
			where pu.id = @idProject)
		
		delete from Exception where id in(
			select f.id
			from Exception f
			join dbo.ProjectUser pu on pu.id = f.idProjectUser
			where pu.id = @idProject)
			
		
		delete from dbo.ProjectUser where id = @idProject
		
	end 
	
	exec dbo.sp_generateProject @localFOlder = 'n', @operacao = 'i', @projectName = 'ops'
	else begin
			if @operacao = 'y' begin
				insert @temp
				select tuc.idTestCase
				,uc.id			 
				from dbo.UseCase uc
				join dbo.TestUseCase tuc on tuc.idUseCase = uc.id
				join dbo.TestCase tc on tc.id = tuc.idTestCase
				join dbo.ProjectUser pu on pu.id = @idProject
				join dbo.Project p on p.id = pu.idProject
				where p.id = @idProject
				
				delete from dbo.TestUseCase where idTestCase in(
						select tuc.idTestCase as id
						from dbo.UseCase uc
						join  @temp tuc on tuc.idUseCase = uc.id
						join dbo.TestCase tc on tc.id = tuc.idTestCase
						join dbo.ProjectUser pu on pu.id = tc.idProjectUser
						join dbo.Project p on p.id = pu.idProject
						where p.id = @idProject
				)
				
				--delete from dbo.UseCase where id in(
				--		select uc.id
				--		from dbo.UseCase uc
				--		join  @temp tuc on tuc.idUseCase = uc.id
				--		join dbo.TestCase tc on tc.id = tuc.idTestCase
				--		join dbo.ProjectUser pu on pu.id = tc.idProjectUser
				--		join dbo.Project p on p.id = pu.idProject
				--		where p.id = @idProject
				--)
				
				delete from dbo.TestCase where id in(
						select tc.id
						from @temp tuc
						join dbo.TestCase tc on tc.id = tuc.idTestCase
						join dbo.ProjectUser pu on pu.id = tc.idProjectUser
						join dbo.Project p on p.id = pu.idProject
						where p.id = @idProject
				)
				delete from Filter where id in(
					select f.id
					from Filter f
					join dbo.ProjectUser pu on pu.id = f.idProjectUser
					join dbo.Project p on p.id = pu.idProject
					where p.id = @idProject)
		
				delete from Exception where id in(
					select f.id
					from Exception f
					join dbo.ProjectUser pu on pu.id = f.idProjectUser
					join dbo.Project p on p.id = pu.idProject
					where p.id = @idProject)
				
				delete from dbo.ProjectUser where id in(
					select pu.id
					from dbo.ProjectUser pu
					join dbo.Project p on p.id = pu.idProject
					where p.id = @idProject)
					
				delete from dbo.Project where id = @idProject
			end
			else begin
				declare @idUser int
				declare @id int
				select @idUser = ID from dbo.TargetUser where userName = @user
				if @operacao = 'i' begin
					 insert dbo.Project (projectName, localFolder, creationDate, userName) values
															(@projectName, @localFolder, GETDATE(), @user)
															
					 set @id = SCOPE_IDENTITY()
		   			insert dbo.ProjectUser(idProject, idUser, openDate) values
																	(@id, @idUser, GETDATE())
				   	
				   		
				end
				else begin
						
						select @id = ID from dbo.Project where projectName = @projectName
						and localFolder = @localFolder
						
						if @id is null begin 
								insert dbo.Project (projectName, localFolder, creationDate, userName) values
															(@projectName, @localFolder, GETDATE(), @user)
								set @id = SCOPE_IDENTITY()
						end
						
						insert dbo.ProjectUser(idProject, idUser, openDate) values
																	(@id, @idUser, GETDATE())
						

				end
			end
	end
		

	