create procedure dbo.sp_generateUser(
		 @userName varchar(50)
)
as
SET NOCOUNT ON 
 begin
	
	declare @id int
	
	select @id = id      
	from dbo.TargetUser
	where userName = @userName
	
	if @id is null begin
			insert dbo.TargetUser (userName) values(@userName)	
	end	

end

