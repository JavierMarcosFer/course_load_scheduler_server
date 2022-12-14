package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.dao.UserStateDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.dto.UserInfoDto;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UserState;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.model.ViewAccountInformation;
import org.dselent.scheduling.server.service.UserService;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UsersDao usersDao;

	@Autowired
	private UsersRolesLinksDao usersRolesLinksDao;
	
	@Autowired
	private CustomDao CustomDao;
	
	@Autowired
	private UserStateDao userStateDao;
	
	public UserServiceImpl()
	{
		//
	}

	/*
	 * (non-Javadoc)
	 * @see org.dselent.scheduling.server.service.UserService#registerUser(org.dselent.scheduling.server.dto.RegisterUserDto)
	 */
	@Transactional
	@Override
	public List<Integer> addUser(RegisterUserDto dto) throws SQLException
	{
		//Add entry to user_state table
		UserState userState = new UserState();
		userState.setState(false);
		
		List<String> userStateColumnNameList = new ArrayList<String>();
		userStateColumnNameList.add(UserState.getColumnName(UserState.Columns.DELETED));
		
		List<String> userStateKeyHolderColumnNameList = new ArrayList<String>();
		userStateKeyHolderColumnNameList.add(UserState.getColumnName(UserState.Columns.CREATED_AT));
		userStateKeyHolderColumnNameList.add(UserState.getColumnName(UserState.Columns.ID));
		userStateKeyHolderColumnNameList.add(UserState.getColumnName(UserState.Columns.UPDATED_AT));
		
		//Special case: this function returns the ID of the entry created in database
		Integer userStateId= userStateDao.insert(userState, userStateColumnNameList, userStateKeyHolderColumnNameList);
		
		//Add user itself
		List<Integer> rowsAffectedList = new ArrayList<>();

		String password = "wpi123";

		String salt = KeyGenerators.string().generateKey();
		String saltedPassword = password + salt;
		PasswordEncoder passwordEncorder = new BCryptPasswordEncoder();
		String encryptedPassword = passwordEncorder.encode(saltedPassword);

		User user = new User();
		user.setUserName(dto.getUserName());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setEncryptedPassword(encryptedPassword);
		user.setUserRole(2);
		user.setSalt(salt);
		user.setPhoneNum(dto.getPhoneNum());
		user.setUserStateId(userStateId);

		List<String> userInsertColumnNameList = new ArrayList<>();
		List<String> userKeyHolderColumnNameList = new ArrayList<>();

		userInsertColumnNameList.add(User.getColumnName(User.Columns.USER_NAME));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.FIRST_NAME));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.LAST_NAME));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.EMAIL));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.PHONE_NUM));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.ENCRYPTED_PASSWORD));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.SALT));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.USER_ROLE));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.USER_STATE_ID));

		userKeyHolderColumnNameList.add(User.getColumnName(User.Columns.ID));
		userKeyHolderColumnNameList.add(User.getColumnName(User.Columns.CREATED_AT));
		userKeyHolderColumnNameList.add(User.getColumnName(User.Columns.UPDATED_AT));

		rowsAffectedList.add(usersDao.insert(user, userInsertColumnNameList, userKeyHolderColumnNameList));

		//

		// for now, assume users can only register with default role id
		// may change in the future

		UsersRolesLink usersRolesLink = new UsersRolesLink();
		usersRolesLink.setUserId(user.getId());
		usersRolesLink.setRoleId(2); // hard coded as regular user

		List<String> usersRolesLinksInsertColumnNameList = new ArrayList<>();
		List<String> usersRolesLinksKeyHolderColumnNameList = new ArrayList<>();

		usersRolesLinksInsertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.USER_ID));
		usersRolesLinksInsertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID));

		usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ID));
		usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.CREATED_AT));
		usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.UPDATED_AT));
		usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.DELETED));

		rowsAffectedList.add(usersRolesLinksDao.insert(usersRolesLink, usersRolesLinksInsertColumnNameList, usersRolesLinksKeyHolderColumnNameList));

		return rowsAffectedList;
	} 
	
	


	@Override
	public void deleteUser(Integer id) throws Exception {
		String columnName = User.getColumnName(User.Columns.USER_STATE_ID);
		
		Integer newValue = 2;
		
		ArrayList<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
			QueryTerm idQueryTerm = new QueryTerm();
			idQueryTerm.setColumnName(User.getColumnName(User.Columns.ID));
			idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			idQueryTerm.setValue(id);
			queryTermList.add(idQueryTerm);
			
		usersDao.update(columnName, newValue, queryTermList);
	}

/*	public UserInfoDto userInfo(Integer user_id) throws Exception{

		List<String> columnNameList = new ArrayList<>();
		columnNameList.add(User.getColumnName(User.Columns.USER_NAME));
		columnNameList.add(User.getColumnName(User.Columns.EMAIL));
		columnNameList.add(User.getColumnName(User.Columns.PHONE_NUM));

		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idQueryTerm = new QueryTerm();
		idQueryTerm.setValue(user_id);
		idQueryTerm.setColumnName(User.getColumnName(User.Columns.ID));
		idQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		queryTermList.add(idQueryTerm);

		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();

		List<User> results = usersDao.select(columnNameList, queryTermList, orderByList);

		if(results.size() == 1 ) {
			User user = results.get(0);
			UserInfoDto.Builder builder = UserInfoDto.builder();
			UserInfoDto userDto = builder.withEmail(user.getEmail())
					.withFirstName(user.getFirstName())
					.withLastName(user.getLastName())
					.withPhonrNum(user.getPhoneNum())
					.withUserName(user.getUserName())
					.withSecondaryEmail(user.getSecondaryEmail())
					.build();

			return userDto;
		}
		else return null;
	}*/
	
	@Override
	public UserInfoDto userInfo(Integer userId) throws Exception{


		List<ViewAccountInformation> results = CustomDao.getAccountInformationWithUserId(userId);
		
		if(results.size() == 1 ) {
			ViewAccountInformation userInfo = results.get(0);
			UserInfoDto.Builder builder = UserInfoDto.builder();
			UserInfoDto userDto = builder
					.withEmail(userInfo.getEmail())
					.withFirstName(userInfo.getFirstName())
					.withLastName(userInfo.getLastName())
					.withPhoneNum(userInfo.getPhoneNum())
					.withUserName(userInfo.getUserName())
					.withSecondaryEmail(userInfo.getSecondaryEmail())
					.withReqCourses(userInfo.getRemaining())
					.build();

			return userDto;
		}
		else return null;
	}
	
	@Override
	public List<Integer> userInfoUpdate(Integer userId, String currentPassword, String newPassword, String confirmNewPassword, String preferredEmail, Long phoneNum) throws Exception{
		//TODO fix update user info
		List<Integer> rowsAffectedList = new ArrayList<>();
		List<QueryTerm> queryTermList = new ArrayList<>();
		List<Object> newValueList = new ArrayList<>();
		
		User user = usersDao.findById(userId);
		
		if(!newPassword.equals(null)){
			if(confirmNewPassword.equals(newPassword)) {
				
				String oldSalt = user.getSalt();
				String oldSaltedPassword = currentPassword + oldSalt;
				PasswordEncoder oldPasswordEncorder = new BCryptPasswordEncoder();
				String currentEncryptedPassword = oldPasswordEncorder.encode(oldSaltedPassword);

				if(user.getEncryptedPassword().equals(currentEncryptedPassword)) {
					String salt = KeyGenerators.string().generateKey();
					String newSaltedPassword = newPassword + salt;
					PasswordEncoder newPasswordEncorder = new BCryptPasswordEncoder();
					String newEncryptedPassword = newPasswordEncorder.encode(newSaltedPassword);
					
					newValueList.add(salt);
					newValueList.add(newEncryptedPassword);
				}
			}
		}
		else {
			newValueList.add(preferredEmail);
			newValueList.add(phoneNum);
			
		}
		
		List<String> userInsertColumnNameList = new ArrayList<>();


		userInsertColumnNameList.add(User.getColumnName(User.Columns.SECONDARY_EMAIL));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.PHONE_NUM));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.SALT));
		userInsertColumnNameList.add(User.getColumnName(User.Columns.ENCRYPTED_PASSWORD));
		
		rowsAffectedList.add(usersDao.updateUser(userInsertColumnNameList, newValueList, queryTermList));
		
		return rowsAffectedList;
		
		
	}
	
	@Override
	public List<UserInfoDto> getAllUserInfo() throws Exception{


		List<User> listOfAdmins = CustomDao.getAllUsersWithRole(1);
		List<User> listOfUsers = CustomDao.getAllUsersWithRole(0);
		listOfUsers.addAll(listOfAdmins);
		List<UserInfoDto> listOfUserdto = new ArrayList<UserInfoDto>();
		
		for(User i : listOfUsers) {
			List<ViewAccountInformation> user = CustomDao.getAccountInformationWithUserId(i.getId());
				for (ViewAccountInformation userInfo : user) {
					UserInfoDto.Builder builder = UserInfoDto.builder();
					UserInfoDto userDto = builder
							.withEmail(userInfo.getEmail())
							.withFirstName(userInfo.getFirstName())
							.withLastName(userInfo.getLastName())
							.withPhoneNum(userInfo.getPhoneNum())
							.withUserName(userInfo.getUserName())
							.withSecondaryEmail(userInfo.getSecondaryEmail())
							.withReqCourses(userInfo.getRemaining())
							.build();
					listOfUserdto.add(userDto);
				}
		}
		return listOfUserdto;
	}
}
