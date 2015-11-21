package cn.itcast.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.IApplicationDao;
import cn.itcast.dao.IApproveInfoDao;
import cn.itcast.domain.Application;
import cn.itcast.domain.ApproveInfo;
import cn.itcast.domain.User;
import cn.itcast.service.IFlowService;
import cn.itcast.utils.Pair;
@Service
@Transactional
public class FlowServiceImpl implements IFlowService {
	@Resource
	private IApplicationDao applicationDao;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private IApproveInfoDao approveInfoDao;
	/**
	 * 提交申请
	 */
	@Override
	public void submit(Application app) {
		//保存一个申请记录
		applicationDao.save(app);
		//启动一个流程实例
		Map<String, Application> map = new HashMap<>();
		map.put("application", app);
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(app.getTemplate().getProcessDefinitionKey(),map);
		//办理提交申请的任务
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.processInstanceId(pi.getId()); //获取当前流程实例的唯一的一个任务
		Task task = query.uniqueResult();
		String taskId = task.getId();
		processEngine.getTaskService().completeTask(taskId);
	}
	/**
	 * 查询我的任务列表
	 */
	public List<Pair<Application, Task>> findTaskList(User currentUser) {
		//根据用户登录名查询对应的任务列表
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks(currentUser.getLoginName());
		//从任务的流程变量中获取对应的一个申请实体
		List<Pair<Application, Task>> list = new ArrayList<>();
		for(Task task : taskList){
			Application application = (Application) processEngine.getTaskService().getVariable(task.getId(), "application");
			Pair<Application, Task>  pair = Pair.of(application, task);
			list.add(pair);
		}
		return list;
	}
	/**
	 * 审批处理
	 */
	@Override
	public void approve(ApproveInfo ai, String taskId) {
		//判断一个任务是否结束
		Task task = processEngine.getTaskService().getTask(taskId); //根据任务id查询任务
		String executionId = task.getExecutionId(); //获得当前任务的执行id
		
		//保存一个审批实体
		approveInfoDao.save(ai);
		//办理任务
		processEngine.getTaskService().completeTask(taskId);
		
		//判断一个任务是否结束
		ProcessInstanceQuery query = processEngine.getExecutionService().createProcessInstanceQuery();
		query.processInstanceId(executionId); //通过任务的执行id在进程表中查找该任务实例
		ProcessInstance pi = query.uniqueResult(); //通过查询结果来确定任务是否完成
		
		if(ai.getApproval()){
			//审批通过
			if(null == pi){
				//当前办理任务是最后一个任务，则把申请状态改为"已通过"
				ai.getApplication().setStatus(Application.STATUS_APPROVED); //持久化对象不用保存,但需要设置级联
			}
			
		}else{
			//审批不通过,如果流程实例没有结束
			if( pi != null ){
				//流程还没有结束，手动结束当前流程实例
				processEngine.getExecutionService().endProcessInstance(executionId, ProcessInstance.STATE_ENDED);
				//修改申请状态为"未通过"
				ai.getApplication().setStatus(Application.STATUS_UNAPPROVED);//持久化对象不用保存,但需要设置级联
			}
			
		}
		
	}
}
