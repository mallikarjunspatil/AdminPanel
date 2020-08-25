package domain;

public class LoginAdmin 
{
	public boolean validation(String user,String password)
	{
		if(user.equals("helix") && password.equals("helix@123"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
