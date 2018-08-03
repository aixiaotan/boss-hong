package com.boss.common.dto.document;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.boss.common.dto.common.EntityDto;

public class DocumentInfoDto extends EntityDto{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4132635379441647588L;

    /**
     * 文件属组，outline-产品大纲
     */
	@NotBlank(message = "文件类型大类不能为空")
    @Length(max=30, message = "文件类型大类为不得超过30个字符")
    private String groupCode;

    /**
     * 文件大小，单位：字节
     */
    private Integer fileSize;

    /**
     * 文件名
     */
	@Length(max=100, message = "文件名不得超过100个字符")
    private String fileName;

    /**
     * 文件备注
     */
	@Length(max=400, message = "文件备注不得超过400个字符")
    private String fileRemark;

    /**
     * 文件顺序
     */
	@DecimalMin("1")
    private Integer rowNo;

    /**
     * 文件存储地址
     */
	@NotBlank(message = "文件存储地址不能为空")
	@Length(max=200, message = "文件存储地址不得超过200个字符")
    private String filePath;

    /**
     * 关联表主键
     */
    private Integer idForeignKey;


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

    public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
}