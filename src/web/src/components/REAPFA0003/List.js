import React from 'react'
import { Row, Col, Table, Popconfirm, Input, Form, Button, Card, Divider } from 'antd'
import moment from 'moment'
import EditableCell from '../EditableCell'

import CreateForm from './Form'

const FUNCTION_CODE = 'REAPFA0003'

const formItemLayout = { labelCol: { span: 8 }, wrapperCol: { span: 16 } }

const Component = ({
  page, dispatch, showCreateModal, loading,
}) => {
  const { Item } = Form
  const onCellChange = (key, dataIndex) => {
    return (value) => {
      const update = page.content.find(o => o.id === key)
      update[dataIndex] = value
      dispatch({ type: `${FUNCTION_CODE}/update`, data: update })
    }
  }
  const columns = [
    {
      title: '应用名称',
      dataIndex: 'name',
      width: '15%',
      key: 'name',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'name')}
        />
      ),
    },
    {
      title: '系统码',
      dataIndex: 'systemCode',
      width: '15%',
      key: 'systemCode',
      render: (text, record) => (
        <EditableCell
          length={30}
          value={text}
          onChange={onCellChange(record.id, 'systemCode')}
        />
      ),
    },
    {
      title: '负责人',
      dataIndex: 'owner',
      width: '15%',
      key: 'owner',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'owner')}
        />
      ),
    },
    {
      title: '创建时间',
      width: '10%',
      dataIndex: 'createTime',
      key: 'createTime',
      render: text => (text ? moment(text).format('YYYY-MM-DD HH:mm:ss') : null),
    },
    {
      title: '备注',
      dataIndex: 'remark',
      width: '20%',
      key: 'remark',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'remark')}
        />
      ),
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: '10%',
      key: 'status',
      render: (text, record) => (record.information.status),
    },
    {
      title: '操作',
      width: '10%',
      dataIndex: 'operation',
      render: (text, record) => {
        return (
          <span>
            <Popconfirm title="确认删除?" onConfirm={() => dispatch({ type: `${FUNCTION_CODE}/delete`, id: record.id })}>
              <a href="#">删除</a>
            </Popconfirm>
            <Divider type="vertical" />
            <a href="#"
              disabled={!record.information.apiDocUrl}
              onClick={(e) => {
              e.preventDefault()
                window.open(record.information.apiDocUrl)
          }}
            >接口文档</a>
          </span>
        )
      },
    },
  ]

  return (
    <Card title="应用管理" bordered={false}>
      <Row>
        <Col md={6}>
          <Item label="应用名称" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: `${FUNCTION_CODE}/setState`,
                  search: {
                    name: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6}>
          <Item label="系统码" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: `${FUNCTION_CODE}/setState`,
                  search: {
                    systemCode: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6}>
          <Item label="负责人" {...formItemLayout}>
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: `${FUNCTION_CODE}/setState`,
                  search: {
                    owner: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col md={6}>
          <Item {...{ wrapperCol: { span: 16, push: 2 } }}>
            <Button type="primary" htmlType="button" icon="search" onClick={() => dispatch({ type: `${FUNCTION_CODE}/query` })} >查询 </Button>
          </Item>
        </Col>
      </Row>
      <Row>
        <CreateForm showCreateModal={showCreateModal} dispatch={dispatch} />
      </Row>
      <Row>
        <Table dataSource={page && page.content}
          pagination={{
            total: page && page.totalElements,
            showTotal: total => `总记录数 ${total} `,
            onChange: (number, size) => (dispatch({ type: `${FUNCTION_CODE}/query`, page: number - 1, size })),
        }}
          rowKey="id"
          columns={columns}
          loading={loading}
          bordered
        />
      </Row>
    </Card>
  )
}
export default Component
