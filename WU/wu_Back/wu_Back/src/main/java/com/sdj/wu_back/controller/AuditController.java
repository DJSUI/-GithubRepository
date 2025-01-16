package com.sdj.wu_back.controller;

import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.entity.Audit;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.service.AuditService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/audit")
public class AuditController {
    @Resource
    private AuditService auditService;
    @PostMapping()
    public Result save(@RequestBody Audit audit ){

        if(audit.getId() == null){
            auditService.add(audit);
        }else{
            auditService.update(audit);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Audit> info = auditService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        auditService.delete(id);
        return Result.success();
    }
}
