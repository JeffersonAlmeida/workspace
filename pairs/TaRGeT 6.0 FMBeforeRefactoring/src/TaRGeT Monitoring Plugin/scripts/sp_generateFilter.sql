alter procedure dbo.sp_generateFilter(
	  @idProjectUser int = null
	 ,@operation varchar(2) = null
	 ,@useCase varchar(500) =  null
	 ,@req varchar(500) = null
	 ,@path varchar(10) = null
)
as begin

		insert dbo.Filter(idProjectUser, type, req, useCase, path) 
		values (@idProjectUser,  @operation, @req, @useCase, @path)	
	
	
	
end
