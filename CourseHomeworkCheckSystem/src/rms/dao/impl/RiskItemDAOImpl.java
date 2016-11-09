package rms.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import rms.common.EffectLevel;
import rms.common.Possibility;
import rms.common.RiskState;
import rms.model.RiskItem;
import rms.model.StateItem;

public class RiskItemDAOImpl implements RiskItemDAO{
	
	private DaoHelper daoHelper;
	
	public RiskItemDAOImpl(){
		this.daoHelper = DaoHelperImpl.getBaseDaoInstance();
	}

	@Override
	public boolean add(RiskItem riskItem) {
		String riskID = riskItem.getRiskId();
		String description = riskItem.getDescription();
		int possibility = transfer(riskItem.getPossibility());
		int effectLevel = transfer(riskItem.getEffect());
		String trigger = riskItem.getTrigger();
		String committerID = riskItem.getCommiterId();
		String followerID = riskItem.getFollowerId();
		int state = riskItem.getState() == RiskState.Removed?1:0;
		List<StateItem> list = riskItem.getList();
		String sql1 = "insert into riskitem values ('"+riskID+"','"
				+description+"',"+possibility+","+effectLevel+",'"
				+trigger+"','"+committerID+"','"+followerID+"',"+state+");";
		boolean state1 = execute(sql1);
		if(!state1)
			return false;
		for(StateItem item:list){
			String sql2 = "insert into stateitem ('";
			String stateID = item.getStateId();
			String rid = item.getRiskId();
			int state0 = item.getState() == RiskState.Removed?1:0;
			String description0 = item.getDescription();
			Calendar start = item.getStart();
			Calendar end = item.getEnd();
			String startTime = transfer(start);
			String endTime = transfer(end);
			long sub = end.getTimeInMillis() - start.getTimeInMillis();
			sql2 = sql2 + stateID + "','" + rid + "'," + state0 + ",'" + description0
					+ "','" + description + "','" + startTime 
					+ "','" + endTime + "'," + sub +";";
			execute(sql2);
		}
		return true;
	}

	@Override
	public boolean remove(String riskID) {
		String sql1 = "delete from riskitem where rid='"+riskID+"';";
		String sql2 = "delete from stateitem where rid='"+riskID+"';";
		boolean state = execute(sql1);
		if(state)
			execute(sql2);
		return state;
	}

	@Override
	public boolean modify(RiskItem riskItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RiskItem> findAll(String userID) {
		String sql = "select rid,description,possibility,effectlevel,`trigger`,committerid,followerid,state from riskitem where committerID='"+userID+"';";
		Connection connection = daoHelper.getConnection();
		List<RiskItem> list = new ArrayList<RiskItem>();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				RiskItem result = new RiskItem();
				result.setRiskId(resultSet.getString(1));
				result.setDescription(resultSet.getString(2));
				int possibility = resultSet.getInt(3);
				switch(possibility){
				case 0:
					result.setPossibility(Possibility.Low);
					break;
				case 1:
					result.setPossibility(Possibility.Medium);
					break;
				case 2:
					result.setPossibility(Possibility.High);
					break;
				default:
					break;
				}
				int effectLevel = resultSet.getInt(4);
				switch(effectLevel){
				case 0:
					result.setEffect(EffectLevel.Low);
					break;
				case 1:
					result.setEffect(EffectLevel.Medium);
					break;
				case 2:
					result.setEffect(EffectLevel.High);
					break;
				default:
					result.setEffect(EffectLevel.Low);
				}
				result.setTrigger(resultSet.getString(5));
				result.setCommiterId(resultSet.getString(6));
				result.setFollowerId(resultSet.getString(7));
				result.setState(resultSet.getInt(8)==0?RiskState.UnRemoved:RiskState.Removed);
				result.setList(this.findStateItem(result.getRiskId()));
				list.add(result);
			}
		} catch (SQLException e) {
			daoHelper.closeConnection(connection);
		}
		return list;
	}

	@Override
	public RiskItem find(String riskID) {
		String sql = "select rid,description,possibility,effect,trigger,committerid,followerid,state from riskitem where riskid='"+riskID+"';";
		Connection connection = daoHelper.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			RiskItem result = new RiskItem();
			result.setRiskId(resultSet.getString(1));
			result.setDescription(resultSet.getString(2));
			int possibility = resultSet.getInt(3);
			switch(possibility){
			case 0:
				result.setPossibility(Possibility.Low);
				break;
			case 1:
				result.setPossibility(Possibility.Medium);
				break;
			case 2:
				result.setPossibility(Possibility.High);
				break;
			default:
				break;
			}
			int effectLevel = resultSet.getInt(4);
			switch(effectLevel){
			case 0:
				result.setEffect(EffectLevel.Low);
				break;
			case 1:
				result.setEffect(EffectLevel.Medium);
				break;
			case 2:
				result.setEffect(EffectLevel.High);
				break;
			default:
				result.setEffect(EffectLevel.Low);
			}
			result.setTrigger(resultSet.getString(5));
			result.setCommiterId(resultSet.getString(6));
			result.setFollowerId(resultSet.getString(7));
			result.setState(resultSet.getInt(8)==0?RiskState.UnRemoved:RiskState.Removed);
			result.setList(this.findStateItem(riskID));
			return result;
		} catch (SQLException e) {
			daoHelper.closeConnection(connection);
		}
		return null;
		
	}

	@Override
	public boolean addStateItem(String riskID, StateItem stateItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteStateItem(String riskID, String stateID) {
		String sql = "delete from stateitem where stateid='"+stateID+"';";
		boolean state = execute(sql);
		return state;
	}

	@Override
	public boolean modifyStateItem(String riskID, StateItem stateItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<StateItem> findStateItem(String riskID) {
		String sql = "select stateid,rid,state,description,start,end,time from stateitem where rid='"+riskID+"';";
		Connection connection = this.daoHelper.getConnection();
		List<StateItem> list = new ArrayList<StateItem>();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				String stateID = resultSet.getString(1);
				String rID = resultSet.getString(2);
				RiskState state = resultSet.getInt(3)==0?RiskState.UnRemoved:RiskState.Removed;
				String description = resultSet.getString(4);
				Calendar start = transfer(resultSet.getString(5));
				Calendar end = transfer(resultSet.getString(6));
				Calendar time = transfer(resultSet.getInt(7));
				StateItem item = new StateItem();
				item.setStateId(stateID);
				item.setRiskId(rID);
				item.setState(state);
				item.setDescription(description);
				item.setStart(start);
				item.setEnd(end);
				item.setTime(time);
				list.add(item);
			}
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
		}
		return list;
	}
	private boolean execute(String sql){
		Connection connection = this.daoHelper.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
			return false;
		}
		boolean state = false;
		try {
			state = statement.execute(sql);
		} catch (SQLException e) {
			this.daoHelper.closeConnection(connection);
			return false;
		}
		this.daoHelper.closeConnection(connection);
		return state;
	}
	
	private int transfer(Possibility p){
		int possibility;
		switch(p){
		case Low:
			possibility = 0;
			break;
		case Medium:
			possibility = 1;
			break;
		case High:
			possibility = 2;
			break;
		default:
			possibility = 3;
		}
		return possibility;
	}
	
	private int transfer(EffectLevel e){
		int effectLevel;
		switch(e){
		case Low:
			effectLevel = 0;
			break;
		case Medium:
			effectLevel = 1;
			break;
		case High:
			effectLevel = 2;
			break;
		default:
			effectLevel = 3;
		}
		return effectLevel;
	}
	
	private String transfer(Calendar calendar){
		StringBuilder sb = new StringBuilder();
		sb.append(calendar.get(Calendar.YEAR));
		sb.append("/");
		sb.append(calendar.get(Calendar.MONTH));
		sb.append("/");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		sb.append("/");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY));
		sb.append("/");
		sb.append(calendar.get(Calendar.MINUTE));
		sb.append("/");
		sb.append(calendar.get(Calendar.SECOND));
		return sb.toString();
	}
	
	private Calendar transfer(String s){
		String[] e = s.split("/");
		int year = Integer.parseInt(e[0]);
		int month = Integer.parseInt(e[1]);
		int day = Integer.parseInt(e[2]);
		Calendar calendar = new GregorianCalendar(year,month,day);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(e[3]));
		calendar.set(Calendar.MINUTE,Integer.parseInt(e[4]));
		calendar.set(Calendar.SECOND,Integer.parseInt(e[5]));
		return calendar;
	}
	
	private Calendar transfer(int millis){
		Calendar calendar = new GregorianCalendar();
		int seconds = millis/1000;
		int minutes = seconds/60;
		int second = seconds%60;
		int hours = minutes/60;
		int minute = minutes%60;
		int days = hours/24;
		int hour = hours%24;
		calendar.set(Calendar.DAY_OF_MONTH, days);
		calendar.set(Calendar.HOUR,hour);
		calendar.set(Calendar.MINUTE,minute);
		calendar.set(Calendar.SECOND,second);
		return calendar;
	}

}
