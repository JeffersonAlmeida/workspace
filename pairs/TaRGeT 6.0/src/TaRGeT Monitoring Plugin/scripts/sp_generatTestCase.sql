create procedure dbo.sp_generateTestCase(
		 @testCaseName varchar(40)
		,@useCaseList varchar(500)
		,@idUser int
		,@idProjectUser int
)

as 
SET NOCOUNT ON
begin		
		declare @tmp table(
			 id int 
		  ,item varchar(50)
		)
		
		declare @idTestCase int	
		declare @id int
		declare @idTmp int
		declare @itemTmp varchar(20)
		declare @idUseCaseTmp int
		
		
		select * from targetuser
		insert dbo.TestCase (idTargetUser, name, date, idProjectUser) values (@idUser, @testCaseName, GETDATE(), @idProjectUser)	
		set @idTestCase = scope_identity()
		
		insert into @tmp
		select id, item from dbo.fn_split(@useCaseList, ', ')
		
		select @id = min(ID) from @tmp
		
		while (not @id is null) begin
				set @idTmp = @id
				
				select @itemTmp = item from @tmp where id = @idTmp				
								
				exec dbo.sp_generateUseCase @useCaseName = @itemTmp, @id = @idUseCaseTmp output
				
				insert dbo.TestUseCase(idUseCase, idTestCase) values (@idUseCaseTmp, @idTestCase)
				
				select @id = MIN(ID) from @tmp where id > @idTmp
				
		end
		
end

