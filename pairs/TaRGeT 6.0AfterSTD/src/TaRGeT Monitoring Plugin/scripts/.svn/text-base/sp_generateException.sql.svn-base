create procedure dbo.sp_generateException(
		@name varchar(50)
   ,@idProjectUser int
) 
as begin 
		
		insert dbo.Exception(name, idProjectUser, throwDate) values
		(@name, @idProjectUser, GETDATE())
end

