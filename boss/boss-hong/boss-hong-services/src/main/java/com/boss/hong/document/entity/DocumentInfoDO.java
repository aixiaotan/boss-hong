package com.boss.hong.document.entity;

import java.util.Date;

import com.boss.common.annotation.Column;
import com.boss.common.annotation.Table;
import com.boss.hong.common.po.AbstractDO;

@Table(name = "tb_fdb_document_info")
public class DocumentInfoDO extends AbstractDO {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5933215428319805029L;
	
    /**
     * 文件属组，outline-产品大纲
     */
	@Column(name = "group_code")
    private String groupCode;

    /**
     * 文件名
     */
	@Column(name = "file_name")
    private String fileName;

    /**
     * 文件本身类型
     */
	@Column(name = "file_size")
    private Integer fileSize;

    /**
     * 文件备注
     */
	@Column(name = "file_remark")
    private String fileRemark;

    /**
     * 文件顺序
     */
	@Column(name = "row_no")
    private Integer rowNo;

    /**
     * 文件存储地址
     */
	@Column(name = "file_path")
    private String filePath;

    /**
     * 关联表主键
     */
	@Column(name = "id_foreign_key")
    private Integer idForeignKey;

	/**
	 * 主键
	 */
	@Column(name = "id")
	private Integer id;
	/**
     * 创建时间
     */
	@Column(name = "created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
	@Column(name = "updated_time")
    private Date updatedTime;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
    public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	/**
     * 文件名
     * @return file_name 文件名
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * 文件备注
     * @return file_remark 文件备注
     */
    public String getFileRemark() {
        return fileRemark;
    }

    /**
     * 文件备注
     * @param fileRemark 文件备注
     */
    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark == null ? null : fileRemark.trim();
    }

    /**
     * 文件顺序
     * @return row_no 文件顺序
     */
    public Integer getRowNo() {
        return rowNo;
    }

    /**
     * 文件顺序
     * @param rowNo 文件顺序
     */
    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    /**
     * 文件存储地址
     * @return file_path 文件存储地址
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 文件存储地址
     * @param filePath 文件存储地址
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * 关联表主键
     * @return id_foreign_key 关联表主键
     */
    public Integer getIdForeignKey() {
        return idForeignKey;
    }

    /**
     * 关联表主键
     * @param idForeignKey 关联表主键
     */
    public void setIdForeignKey(Integer idForeignKey) {
        this.idForeignKey = idForeignKey;
    }
}