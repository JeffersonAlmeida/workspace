create function dbo.fn_split (@strInput varchar(max), @separador varchar(5))
returns @result table (id int identity(1,1), item varchar(50))
as
begin
declare @pStart int
declare @pEnd int

set @pStart = 1
while @pStart > 0 and @pStart <= len(@strInput) begin
  set @pEnd = charindex(isnull(@separador,''),isnull(@strInput,''),@pStart)
  if @pEnd = 0 begin
    set @pEnd = len(@strInput)+1
  end
	if @pEnd > @pStart begin
	  insert @result (item) values (ltrim(rtrim(replace(substring(@strInput,@pStart,@pEnd-@pStart),'''',''))))
	end
	set @pStart = @pEnd+1
end
return
end
