package com.qing.activiti.web;

import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BpmnController {

    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;

    @Autowired
    public BpmnController(RepositoryService repositoryService, RuntimeService runtimeService,
                          TaskService taskService) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @GetMapping(value = "deploy")
    public void deployProcess() {
        // 创建流程引擎
        //ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 得到流程存储服务组件
        //RepositoryService repositoryService = engine.getRepositoryService();
        // 得到运行时服务组件
        //RuntimeService runtimeService = engine.getRuntimeService();
        // 获取流程任务组件
        //TaskService taskService = engine.getTaskService();
        // 部署流程文件
        //repositoryService.createDeployment()
                //.addClasspathResource("bpmn/test.bpmn").deploy();
        // 启动流程
        //runtimeService.startProcessInstanceByKey("process1");
        // 查询第一个任务
        List<Task> tasks = taskService.createTaskQuery().list();//.singleResult();
        for (Task task: tasks) {
            System.out.println("第一个任务完成前，当前任务名称：" + task.getName());
            // 完成第一个任务
            //taskService.complete(task.getId());
            // 查询第二个任务
            List<Task> tasks1 = taskService.createTaskQuery().list();//.singleResult();
            for (Task task1: tasks1) {
                System.out.println("第二个任务完成前，当前任务名称：" + task1.getName());
                // 完成第二个任务（流程结束）
                taskService.complete(task1.getId());
                List<Task> tasks2 = taskService.createTaskQuery().list();//.singleResult();
                tasks2.forEach(task2 -> System.out.println("流程结束后，查找任务：" + task2));
                //System.out.println("流程结束后，查找任务：" + task);
            }
        }
    }
}
