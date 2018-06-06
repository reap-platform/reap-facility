import React from 'react'
import { Row, Col, Table, Popconfirm, Input, Form, Button } from 'antd'
import EditableCell from '../EditableCell'
import FunctionForm from './ConfigForm'
import styles from './ConfigList.less'

const Component = ({
  page, dispatch, showCreateModal, loading,
}) => {
  const { Item } = Form
  const onCellChange = (key, dataIndex) => {
    return (value) => {
      const update = page.content.find(o => o.id === key)
      update[dataIndex] = value
      dispatch({ type: 'REAPFA0001/update', func: update })
    }
  }
  const columns = [
    {
      title: '应用',
      dataIndex: 'application',
      width: '10%',
      key: 'application',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'application')}
        />
      ),
    },
    {
      title: '环境',
      dataIndex: 'profile',
      width: '5%',
      key: 'profile',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'profile')}
        />
      ),
    },
    {
      title: '标签',
      dataIndex: 'label',
      width: '10%',
      key: 'label',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'label')}
        />
      ),
    },
    {
      title: '参数名',
      dataIndex: 'name',
      width: '30%',
      key: 'name',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'name')}
        />
      ),
    },
    {
      title: '参数值',
      width: '30%',
      dataIndex: 'value',
      key: 'value',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'value')}
        />
      ),
    },
    {
      title: '操作',
      width: '5%',
      dataIndex: 'operation',
      render: (text, record) => {
        return (
          page && page.content && page.content.length > 0 ?
            (
              <Popconfirm title="确认删除?" onConfirm={() => dispatch({ type: 'REAPFA0001/delete', id: record.id })}>
                <a href="#">删除</a>
              </Popconfirm>
            ) : null
        )
      },
    },
  ]

  return (
    <Col>
      <Row gutter={12} className={styles.searchForm}>
        <Col span={4}>
          <Item label="归属系统">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    application: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="环境">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    profile: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="标签">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    label: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="参数名">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    name: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="参数值">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0001/setState',
                  search: {
                    value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4} >
          <Item>
            <Button
              type="primary"
              htmlType="button"
              icon="search"
              onClick={
                () => dispatch({ type: 'REAPFA0001/query' })
              }
            >
            查询
            </Button>
          </Item>
        </Col>
      </Row>
      <Col>
        <FunctionForm showCreateModal={showCreateModal} dispatch={dispatch} />
      </Col>
      <Col>
        <Table dataSource={page && page.content}
          pagination={{
            total: page && page.totalElements,
            showTotal: total => `总记录数 ${total} `,
            onChange: (number, size) => (dispatch({ type: 'REAPFA0001/query', page: number - 1, size })),
        }}
          rowKey="id"
          columns={columns}
          loading={loading}
          bordered
        />
      </Col>
    </Col>
  )
}
export default Component
