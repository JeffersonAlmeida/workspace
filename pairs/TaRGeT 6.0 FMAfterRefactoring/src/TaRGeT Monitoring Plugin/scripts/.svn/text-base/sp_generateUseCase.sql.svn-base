create procedure dbo.sp_generateUseCase(
		 @id int output
		,@useCaseName varchar(20)		
)
as 
SET NOCOUNT ON
begin
		declare @useCaseTmp varchar(20)
		select * from targetuser
		select @useCaseTmp = useCaseName
					,@id = id
		from dbo.UseCase
		where useCaseName = @useCaseName
		
		if @useCaseTmp is null begin
				insert dbo.UseCase (useCaseName) values (@useCaseName)
				set @id = scope_identity()
		end

end